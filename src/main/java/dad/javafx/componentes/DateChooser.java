package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class DateChooser extends GridPane implements Initializable {
	
	//model
	
	ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();
	
    @FXML
    private ComboBox<String> dayCombo;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private ComboBox<String> yearCombo;
	
    public DateChooser() {
    	super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		diasLista(31);
		monthCombo.getItems().addAll("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
		for (int i = LocalDate.now().getYear(); i >= 1900; i--) {
			yearCombo.getItems().add(i+"");
		}
		
		//dayCombo.itemsProperty().bind(days);
		//dayCombo.getItems().addAll(days);
		dayCombo.getSelectionModel().select(LocalDate.now().getDayOfMonth()-1);
		//monthCombo.itemsProperty().bind(month);
		monthCombo.getSelectionModel().select(LocalDate.now().getMonthValue()-1);
		//yearCombo.itemsProperty().bind(year);
		yearCombo.getSelectionModel().selectFirst();
		
		dayCombo.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			editarFecha(nv);
		});
		
		yearCombo.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
			int selecM = monthCombo.getSelectionModel().getSelectedIndex();
			int selecD = Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem());
			if (selecM == 1) {
				if (Year.of(Integer.parseInt(yearCombo.getSelectionModel().getSelectedItem())).isLeap()) {
					diasLista(29);
					if (selecD > 29) {
						dayCombo.getSelectionModel().select(28);
					} else {
						dayCombo.getSelectionModel().select(selecD-1);
					}
				} else {
					diasLista(28);
					if (selecD > 28) {
						dayCombo.getSelectionModel().select(27);
					} else {
						dayCombo.getSelectionModel().select(selecD-1);
					}
				}
			}
			editarFecha(nv);
		});
		
		monthCombo.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

			comprobatFecha();
			editarFecha(nv);
			
		});

	}
	
	private void comprobatFecha() {
		int selecM = monthCombo.getSelectionModel().getSelectedIndex();
		int selecD = Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem());
		
		dayCombo.getItems().clear();
		
		if (selecM == 1) {
			if (Year.of(Integer.parseInt(yearCombo.getSelectionModel().getSelectedItem())).isLeap()) {
				diasLista(29);
				if (selecD > 29) {
					dayCombo.getSelectionModel().select(28);
				} else {
					dayCombo.getSelectionModel().select(selecD-1);
				}
			} else {
				diasLista(28);
				if (selecD > 28) {
					dayCombo.getSelectionModel().select(27);
				} else {
					dayCombo.getSelectionModel().select(selecD-1);
				}
			}
		} else if (selecM == 0 || selecM == 2 || selecM == 4 || selecM == 6 || selecM == 7 || selecM == 9 || selecM == 11 ) {
			diasLista(31);
			dayCombo.getSelectionModel().select(selecD-1);
		} else {
			diasLista(30);
			if (selecD > 30) {
				dayCombo.getSelectionModel().select(29);
			} else {
				dayCombo.getSelectionModel().select(selecD-1);
			}
		}

	}
	
	private void editarFecha(String nv) {
		if(nv != null) {

			LocalDate localDate = LocalDate.parse(yearCombo.getSelectionModel().getSelectedItem()+"-"+
					(String.format("%02d", (monthCombo.getSelectionModel().getSelectedIndex()+1)))+"-"+
					(String.format("%02d", Integer.parseInt(dayCombo.getSelectionModel().getSelectedItem()))));
			
			date.set(localDate);
			
			System.out.println(date.toString());
		}

	}
	
	private void diasLista(int num) {
		for (int i = 1; i <= num; i++) {
			dayCombo.getItems().add(""+i);
		}
	}

	public final ObjectProperty<LocalDate> dateProperty() {
		return this.date;
	}
	

	public final LocalDate getDate() {
		return this.dateProperty().get();
	}
	

	public final void setDate(final LocalDate date) {
		this.dateProperty().set(date);
	}
	

}
