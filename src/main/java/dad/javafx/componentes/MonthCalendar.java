package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MonthCalendar extends GridPane implements Initializable {
	
	
    @FXML
    private Label mesLabel;
    
    // model
    
    private IntegerProperty month = new SimpleIntegerProperty();
    private IntegerProperty year = new SimpleIntegerProperty();
    private String[] monthList = {"Enero" , "Febrero" , "Marzo", "Abril", "Mayo", "Junio" , "Julio", "Agosto" , "Septiembre", "Octubre",
    		"Noviembre", "Diciembre" };
	
    public MonthCalendar() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MonthCalendarView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		year.addListener((o, ov, nv ) -> onListener());
		month.addListener((o, ov, nv ) -> onListener());
		year.set(2050);
		month.set(1);
	}

	private void onListener() {
		for (int i = 8; i < 50; i++) {
			Label f = new Label();
			f = (Label) getChildren().get(i);
			f.setText("");
		}
		mesLabel.setText(monthList[month.get()]);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year.get(), month.get(), 0);
		int dayStart = calendar.get(Calendar.DAY_OF_WEEK)+7;
		int dayStop = numeroDeDiasMes(month.get()+1, year.get());
		for (int i = 1; i <= dayStop; i++) {
			Label f = new Label();
			f = (Label) getChildren().get(dayStart);
			dayStart++;
			f.setText(i + "");
		}
	}
	
	private static int numeroDeDiasMes(int mes, int annio){
	    int numeroDias=-1;
	 
	        switch(mes){
	            case 1:
	            case 3:
	            case 5:
	            case 7:
	            case 8:
	            case 10:
	            case 12:
	                numeroDias=31;
	                break;
	            case 4:
	            case 6:
	            case 9:
	            case 11:
	                numeroDias=30;
	                break;
	            case 2:
	 
	                if(esBisiesto(annio)){
	                    numeroDias=29;
	                }else{
	                    numeroDias=28;
	                }
	                break;
	 
	        }
	 
	    return numeroDias;
	}

	private static boolean esBisiesto(int anio) {
		 
	    GregorianCalendar calendar = new GregorianCalendar();
	    boolean esBisiesto = false;
	    if (calendar.isLeapYear(anio)) {
	        esBisiesto = true;
	    }
	    return esBisiesto;
	 
	}

	public final IntegerProperty monthProperty() {
		return this.month;
	}
	

	public final int getMonth() {
		return this.monthProperty().get();
	}
	

	public final void setMonth(final int month) {
		this.monthProperty().set(month);
	}
	

	public final IntegerProperty yearProperty() {
		return this.year;
	}
	

	public final int getYear() {
		return this.yearProperty().get();
	}
	

	public final void setYear(final int year) {
		this.yearProperty().set(year);
	}
	

}
