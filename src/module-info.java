module Splendor {
	exports persistentie;
	exports ui;
	exports util;
	exports gui;
	exports main;
	exports domein;
	exports testen;
	exports dto;
	exports exceptions;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
	
	opens main to javafx.fxml, javafx.graphics;
	opens gui to javafx.fxml, javafx.graphics;
}