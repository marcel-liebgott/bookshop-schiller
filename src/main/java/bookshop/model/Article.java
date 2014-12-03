package bookshop.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;

@Entity
public class Article extends Product{
		
		public static enum ArticleId {ALL, BOOK, DVD, CD};
		
		private String beschreibung;
		private String publisher;
		private String id;
		private ArticleId type;
		
		//!!! - Kategorie manuell in der Main neben Artikel definieren 
		//oder im Artikel direkt hinzufügen im Kostruktor
		//private Category category;
		
		@Deprecated
		protected Article() {}
		
		//@SuppressWarnings("deprecation")//was ist das?!
		public Article(String title, Money price, String beschreibung, String publisher, String id, ArticleId type){
			super(title, price, Units.METRIC);
			this.beschreibung = beschreibung;
			this.publisher = publisher;
			this.id = id;
			this.type = type;
		}
		
		
		public String getBeschreibung(){
			return beschreibung;
		}
		
		public String getPublisher(){
			return publisher;
		}
		
		/*
		public void setCategory(Category category){
			this.category = category;
		}
		
		public Category getCategory(){
			return this.category;
		}
		*/
		
		
		public ArticleId getType(){
			return type;
		}
		
		public String getId(){
			return id;
		}

	}

