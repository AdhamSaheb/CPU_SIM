package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class Controller implements Initializable {

	public static int ProcessNumber;
	String method = new String();

	@FXML
	private ComboBox<String> AlgMenu;

	@FXML
	private TextField QuantumField;

	@FXML
	private TextField NumberField;

	@FXML
	private Button GenerateButton;

	@FXML
	private Button ResetButton;

	@FXML
	private Button StartButton;

	@FXML
	private Button ShowButton;
	
	@FXML
	private Button quit;

	 @FXML
	    private TextArea area;


	public static  ArrayList<Integer> ganttChart = new ArrayList<Integer>() ;

	@FXML
	void Generate(ActionEvent event) {
		
		Scheduler.Processes.clear();
		Scheduler.TimeQuantum = Integer.parseInt(QuantumField.getText() ) ; 
		
		
		double ta1 ; 
		double ta2 ; 
		double ta3 ; 
		double ta4 ; 
		double ta5 ; 
		 

		FadeTransition ft = new FadeTransition(Duration.millis(1000), GenerateButton);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);
		
		
		ft.setOnFinished(e->{ 	
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText(ProcessNumber + " Processes has been generated ");
			alert.show();
	
  });
		ft.play();
		ProcessNumber = Integer.parseInt(NumberField.getText());

		for (int i = 0; i < ProcessNumber; i++) {
			Process process = new Process();
			Scheduler.Processes.add(process);

		}
		
		

		ganttChart = Scheduler.FCFS(); 
		ta1 = avgwait(Scheduler.Processes) ;
		
		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Scheduler.Processes.get(i).resetProcess();

		}
		
		
		
		ganttChart = Scheduler.Priority();
		ta2 = avgwait(Scheduler.Processes) ;
		
		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Scheduler.Processes.get(i).resetProcess();

		}
		
		ganttChart = Scheduler.RR(); 
		ta3 = avgwait(Scheduler.Processes) ;
		
		
		
		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Scheduler.Processes.get(i).resetProcess();

		}
		
		ganttChart = Scheduler.SJF(); 
		ta4 = avgwait(Scheduler.Processes) ;
		
		
		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Scheduler.Processes.get(i).resetProcess();

		}
		ganttChart = Scheduler.SRTF();
		ta5 = avgwait(Scheduler.Processes) ;
		
		
		
		
		area.replaceSelection(
				"\nAVG WAIT IN FCFS - >  " + ta1 
				+ "\nAVG WAIT IN PRORITY - >  " + ta2
				+"\nAVG WAIT IN RR - >  " + ta3
				+"\nAVG WAIT IN SJF - >  " + ta4 
				+"\nAVG WAIT IN SRTF - >  " + ta5 
				+ "\n----------------------------------------\n");

	
	}

	@FXML
	void GetNumber(ActionEvent event) {

		ProcessNumber = Integer.parseInt(NumberField.getText());

	}

	
	@FXML
	void quit(ActionEvent event) {

		System.exit(0);

	}

	@FXML
	void GetQuantum(ActionEvent event) {
		Scheduler.TimeQuantum = Integer.parseInt(QuantumField.getText());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AlgMenu.getItems().addAll("FCFS", "SJF", "SRTF", "RR","Priority","PP");
		area.setEditable(false);
		

	}

	// TODO Auto-generated method stub
	@FXML
	String method(ActionEvent event) {
		method = AlgMenu.getValue().toString();

		if (method.compareTo("RR") == 0) {
			QuantumField.setEditable(true);

		}
		if (method.compareTo("RR") != 0 ) {
			QuantumField.setEditable(true);

		}
		return method;

	}

	@FXML
	void showTable(ActionEvent event) {

		TableController.Processes.clear();

		for (int i = 0; i < Scheduler.Processes.size(); i++) {
			Process process = Scheduler.Processes.get(i);

			TableController.Processes.add(process);
		}

		Main.PrimaryStage.setScene(Main.showScene);
		Main.PrimaryStage.centerOnScreen();

	}

	@FXML
	 void StartButtonPressed(ActionEvent event) {
		ganttChart.clear();
		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Scheduler.Processes.get(i).resetProcess();

		}

		switch (method) {
		case "RR": {
			Scheduler.TimeQuantum = Integer.parseInt(QuantumField.getText());

			ganttChart = Scheduler.RR();
			break;
		}
		case "FCFS": {

			ganttChart = Scheduler.FCFS();
			break;
		}
		case "SJF": {

			ganttChart = Scheduler.SJF();
			break;
		}
		case "PP": {

			ganttChart = Scheduler.PP();
			break;
		}
		case "SRTF": {

			ganttChart = Scheduler.SRTF();
			break;
		}
		
		case "Priority": {

			ganttChart = Scheduler.Priority();
			break;
		}

		}

		StartController.Processes.clear();
		Scheduler.displayGanttChart(ganttChart);
		

		for (int i = 0; i < Scheduler.Processes.size(); i++) {

			Process process = Scheduler.Processes.get(i);

			StartController.Processes.add(process);
		}

		Main.PrimaryStage.setScene(Main.FinalScene);
		Main.PrimaryStage.centerOnScreen();
        //Stage stage = new Stage() ; 
        //stage.setScene(Main.FinalScene); 
        //stage.show();
	}

	@FXML
	void ResetButtonPressed(ActionEvent event) {
		Scheduler.Processes.clear();
		FadeTransition ft = new FadeTransition(Duration.millis(1000), ResetButton);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);
		
		ft.play();
		
		
		

		Scheduler.Processes.clear();

		TableController.Processes.clear();
		QuantumField.clear();
		NumberField.clear();

	}
	
	double avgwait (ArrayList<Process> Processes ) {
		
		int sum = 0 ; 
		
		for (int i = 0 ; i<Processes.size(); i ++)  sum+= Processes.get(i).waitingTime; 
			
			sum /= Processes.size() ; 
			
			return sum ;
		
		
		
	} 
	
	
	

}
