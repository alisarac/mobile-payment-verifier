package com.alisrc.ios.payment;

public class IosVerifyResponse {
	Receipt receipt;
	int status;
	
	public static class Receipt {
		String unique_identifier;
		long original_transaction_id;
		String bvrs;
		long transaction_id;
		int quantity;
		String unique_vendor_identifier;
		long item_id;
		int product_id;
		String purchase_date;
		String purchase_date_pst;
		long purchase_date_ms;
		String original_purchase_date;
		long original_purchase_date_ms;
		String original_purchase_date_pst;
		String bid;

		public String getUnique_identifier() {
			return unique_identifier;
		}
		public void setUnique_identifier(String unique_identifier) {
			this.unique_identifier = unique_identifier;
		}
		public long getOriginal_transaction_id() {
			return original_transaction_id;
		}
		public void setOriginal_transaction_id(long original_transaction_id) {
			this.original_transaction_id = original_transaction_id;
		}
		public String getBvrs() {
			return bvrs;
		}
		public void setBvrs(String bvrs) {
			this.bvrs = bvrs;
		}
		public long getTransaction_id() {
			return transaction_id;
		}
		public void setTransaction_id(long transaction_id) {
			this.transaction_id = transaction_id;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public String getUnique_vendor_identifier() {
			return unique_vendor_identifier;
		}
		public void setUnique_vendor_identifier(String unique_vendor_identifier) {
			this.unique_vendor_identifier = unique_vendor_identifier;
		}
		public long getItem_id() {
			return item_id;
		}
		public void setItem_id(long item_id) {
			this.item_id = item_id;
		}
		public int getProduct_id() {
			return product_id;
		}
		public void setProduct_id(int product_id) {
			this.product_id = product_id;
		}
		public String getPurchase_date() {
			return purchase_date;
		}
		public void setPurchase_date(String purchase_date) {
			this.purchase_date = purchase_date;
		}
		public String getPurchase_date_pst() {
			return purchase_date_pst;
		}
		public void setPurchase_date_pst(String purchase_date_pst) {
			this.purchase_date_pst = purchase_date_pst;
		}
		public long getPurchase_date_ms() {
			return purchase_date_ms;
		}
		public void setPurchase_date_ms(long purchase_date_ms) {
			this.purchase_date_ms = purchase_date_ms;
		}
		public String getOriginal_purchase_date() {
			return original_purchase_date;
		}
		public void setOriginal_purchase_date(String original_purchase_date) {
			this.original_purchase_date = original_purchase_date;
		}
		public long getOriginal_purchase_date_ms() {
			return original_purchase_date_ms;
		}
		public void setOriginal_purchase_date_ms(long original_purchase_date_ms) {
			this.original_purchase_date_ms = original_purchase_date_ms;
		}
		public String getOriginal_purchase_date_pst() {
			return original_purchase_date_pst;
		}
		public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
			this.original_purchase_date_pst = original_purchase_date_pst;
		}
		public String getBid() {
			return bid;
		}
		public void setBid(String bid) {
			this.bid = bid;
		}

    }
	
	IosVerifyResponse(){
		
	}
	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}