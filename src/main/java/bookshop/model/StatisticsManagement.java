package bookshop.model;

import static org.joda.money.CurrencyUnit.EUR;

import java.time.LocalDateTime;
import java.util.Optional;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;


public class StatisticsManagement {
	
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime date;
	
	/**
	 * Constructor for StatisticsManagement
	 * @param orderManager
	 * @param inventory
	 * @param date
	 */
	public StatisticsManagement(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory, BusinessTime date){
		this.orderManager = orderManager;
		this.inventory = inventory;
		this.date = date;
	}
	
	/**
	 * Return an order of all articles sold and reservations booked
	 * @param userAccount
	 * @return Order
	 */
	public Order getGesOrdersSell(UserAccount userAccount){
		Order order1 = new Order(userAccount);
		Role role = new Role("ROLE_CUSTOMER");
		
		for(Order order : orderManager.find(OrderStatus.COMPLETED)){
			for(OrderLine orderLine : order.getOrderLines()){
				order1.add(orderLine);
			}
		}
		for(Order order : orderManager.find(OrderStatus.PAID)){
			if(order.getUserAccount().hasRole(role) == true){
				for(OrderLine orderLine : order.getOrderLines()){				
						order1.add(orderLine);	
				}
			}
		}
		return order1;
	}
	
	/**
	 * Return an order of all restock orders
	 * @param userAccount
	 * @return Order
	 */
	public Order getGesOrdersBuy(UserAccount userAccount){
		Order order1 = new Order(userAccount);
		Role role = new Role("ROLE_CUSTOMER");
		
		for(Order order : orderManager.find(OrderStatus.PAID)){

			if(order.getUserAccount().hasRole(role) == false){
				for(OrderLine orderLine : order.getOrderLines()){				
						order1.add(orderLine);	
				}
			}
		}	
		return order1;
	}
	
	/**
	 * Return an orderLine, which collect all orders of a given item.
	 * If type = true, you get all orders of restock an given item 
	 * If type = false, you get all orders of sell an given item 
	 * @param type
	 * @param item
	 * @return OrderLine
	 */
	public OrderLine getStatistic(boolean type, InventoryItem item){
		LocalDateTime time = date.getTime();
		time = time.minusDays(7);
		Quantity quantity = item.getQuantity();
		quantity = quantity.subtract(item.getQuantity());
		Quantity quantity1 = quantity;
		Article article = (Article) item.getProduct();
		Product product = new Product(item.getProduct().getName(), item.getProduct().getPrice(), Units.METRIC);		
		for(Order order : orderManager.find(time, date.getTime())){

			if(order.isPaid()==true){
			
			
				for(OrderLine orderLine : order.getOrderLines()){
					
					ProductIdentifier name1 = item.getProduct().getIdentifier();
					ProductIdentifier name2 = orderLine.getProductIdentifier();
					
					if(name1.equals(name2)== true){
							quantity = quantity.add(orderLine.getQuantity());							
							product.setPrice(article.getStockPrice());
					}
						
				}	
			
			}else{
				if(order.isCompleted() == true){
					for(OrderLine orderLine : order.getOrderLines()){
						
						ProductIdentifier name1 = item.getProduct().getIdentifier();
						ProductIdentifier name2 = orderLine.getProductIdentifier();
						
						
						if(name1.equals(name2)== true){
								quantity1 = quantity1.add(orderLine.getQuantity());
							
						}
							
					}	
				}
			}
		}	
	
		OrderLine returnOrderLineBuy = new OrderLine(product,quantity);
		OrderLine returnOrderLineSell = new OrderLine(item.getProduct(),quantity1);

	if(type == true){
		return returnOrderLineBuy;
	}else{
		return returnOrderLineSell;
	}
	}
	
	/**
	 * Return an orderline, which collect all reservations of a given event. (last Week)
	 * @param event
	 * @return OrderLine
	 */
	public OrderLine statisticOfReservation(String event){
		LocalDateTime time = date.getTime();
		time = time.minusDays(7);
		Quantity quantity = Units.of(0);
		Product product = new Product(event, Money.of(EUR, 5.00), Units.METRIC);
		
		for(Order order : orderManager.find(time, date.getTime())){

			if(order.isPaid()==true){
			
			
				for(OrderLine orderLine : order.getOrderLines()){
					String event2 = orderLine.getProductName();
					
					
					if(event.equals(event2)== true){
							quantity = quantity.add(orderLine.getQuantity());
					}
						
				}	
			}
		}
		
		OrderLine orderLine = new OrderLine(product ,quantity);		
		return orderLine;	
	}
	
	/**
	 * Return an orderline, which collect all reservations of a given event. (all time)
	 * @param event
	 * @return OrderLine
	 */
	public OrderLine getGesStatisticsOfReservation(String event){
		LocalDateTime time = date.getTime();
		time = time.minusDays(7);
		Quantity quantity = Units.of(0);
		Product product = new Product(event, Money.of(EUR, 5.00), Units.METRIC);
		Role role = new Role("ROLE_CUSTOMER");
		
		for(Order order : orderManager.find(OrderStatus.PAID)){

			if(order.getUserAccount().hasRole(role) == true){
			
			
				for(OrderLine orderLine : order.getOrderLines()){
					String event2 = orderLine.getProductName();
					
					
					if(event.equals(event2)== true){
							quantity = quantity.add(orderLine.getQuantity());
					}
						
				}	
			}
		}
		
		OrderLine orderLine = new OrderLine(product ,quantity);		
		return orderLine;	
	}
	
	/**
	 * Return your profit (all time)
	 * @param order
	 * @return Money
	 */
	public Money getProfit(Order order){
		Money result = Money.of(EUR, 0.00);
		Optional<InventoryItem> item;
		Article article;
		for(OrderLine orderLine : order.getOrderLines()){
			Quantity quantity = Units.of(0);
			Quantity add = Units.of(1);
			item = inventory.findByProductIdentifier(orderLine.getProductIdentifier());
			article = (Article) item.get().getProduct();	
				while(quantity.isLessThan(orderLine.getQuantity())){
					result = result.plus(article.getStockPrice());
					quantity = quantity.add(add);
				}	
		}
		return result;
	}

}
