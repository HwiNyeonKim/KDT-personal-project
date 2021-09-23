package com.example.cccoffeebackendfromatoz.repository;

public class CommonSQL {
	public static String SQL_SELECT_ALL = "SELECT * FROM %s";
	public static final String SQL_SELECT_BY_CONDITION = "SELECT * FROM %s WHERE %s = :%s";
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM %s WHERE %s = UUID_TO_BIN(:%s)";
	public static final String SQL_SELECT_BY_RANGE_CONDITION = "SELECT * FROM %s WHERE %s BETWEEN :%s and :%s";
	public static final String SQL_DELETE_BY_ID = "DELETE FROM %s WHERE %s = UUID_TO_BIN(:%s)";
	public static final String SQL_DELETE_ALL = "DELETE FROM %s";

	// for specific domain
	public static final String SQL_INSERT_PRODUCT =
			"INSERT INTO %s(product_id, product_name, category, price, description, created_at, updated_at) VALUES (UUID_TO_BIN(:productId), :productName, :category, :price, :description, :createdAt, :updatedAt)";
	public static final String SQL_INSERT_ORDER
			= "INSERT INTO %s(order_id, email, address, postcode, order_status, created_at, updated_at) VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createdAt, :lastModifiedAt)";

}
