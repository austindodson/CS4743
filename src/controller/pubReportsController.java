package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import gateway.DBGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.Author;
import model.AuthorBook;
import model.Book;
import model.Publisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class pubReportsController {
	
	private Logger logger = LogManager.getLogger(pubReportsController.class);
	
	@FXML
	private ChoiceBox<Publisher> publishers;
	@FXML
	private Button submit;
	
	private DBGateway gateway;
	
	private XSSFWorkbook workbook;
	
	private XSSFSheet sheet;
	
	private Publisher pub;
	
	private ObservableList<Publisher> publisher; 
	
	private ArrayList<Book> books;
	
	private FileChooser fileChooser;
	private int index;

	public pubReportsController(DBGateway gateway) {
		this.gateway = gateway;
		publisher = FXCollections.observableArrayList(gateway.getPublishers());
		this.books = new ArrayList<Book>();
		this.fileChooser = new FileChooser();
		this.workbook = new XSSFWorkbook();
		this.index=0;
	}
	
	@FXML
	public void initialize() {
		setFields();
		setButtonHandlers();
	}
	
	private void generateExcel(Publisher pub, ArrayList<Book> books) {

		CellStyle style= workbook.createCellStyle();
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        
        XSSFFont font= workbook.createFont();
        font.setFontHeightInPoints((short)22);
        font.setBold(true);
        font.setItalic(false);
        
        XSSFFont font2= workbook.createFont();
        font2.setBold(true);
        
		ArrayList<Author> authors = new ArrayList<Author>();
		double runningTotal = 0;
		LocalDateTime now = LocalDateTime.now();
		
		Row row = sheet.createRow(0);
	    style.setFont(font);
		row.setHeightInPoints(35);
		Cell cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Royalty Report");
		
		row = sheet.createRow(1);
		row.setHeightInPoints(35);
		cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Publisher: "+pub);
		
		Row row2 = sheet.createRow(2);
		cell = row2.createCell(0);
		style.setFont(font2);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");
		cell.setCellStyle(style);
		cell.setCellValue("Report generated on "+formatter.format(now));
		
		row2 = sheet.createRow(4);
		cell = row2.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("Book Title");
		cell = row2.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue("ISBN");
		cell = row2.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("Author");
		cell = row2.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("Royalty");
		
		index = 5;
		for(Book book: books) {
			authors = gateway.getAuthorsForBook(book);
			if (authors.size() == 1) {
				AuthorBook ab = gateway.getAuthorBook(authors.get(0).getId(), book.getId());
				double roy = (double) (ab.getRoyalty()*.01);
				runningTotal+=roy;
				
				row2 = sheet.createRow(index);
				cell = row2.createCell(0);
				cell.setCellValue(book.getTitle());
				cell = row2.createCell(1);
				cell.setCellValue(book.getIsbn());
				cell = row2.createCell(2);
				cell.setCellValue(authors.get(0).getFirstname()+ " " + authors.get(0).getLastname());
				cell = row2.createCell(3);
				cell.setCellValue(roy+"%");
				index++;
				
				row2 = sheet.createRow(index);
				cell = row2.createCell(2);
				cell.setCellStyle(style);
				cell.setCellValue("Total Royalty");
				cell = row2.createCell(3);
				cell.setCellStyle(style);
				cell.setCellValue(runningTotal+"%");
				
				index+=2;
				runningTotal = 0;
			}
			else {
				int authIndex = 0;
				for(Author auth: authors) {
					if (authIndex==0) {
						AuthorBook ab = gateway.getAuthorBook(auth.getId(), book.getId());
						double roy = (double) (ab.getRoyalty()*.01);
						runningTotal+=roy;
						row2 = sheet.createRow(index);
						cell = row2.createCell(0);
						cell.setCellValue(book.getTitle());
						cell = row2.createCell(1);
						cell.setCellValue(book.getIsbn());
						cell = row2.createCell(2);
						cell.setCellValue(auth.getFirstname()+ " " + auth.getLastname());
						cell = row2.createCell(3);
						cell.setCellValue(roy+"%");
						index++;
						authIndex++;
					}
					else {
						AuthorBook ab = gateway.getAuthorBook(auth.getId(), book.getId());
						double roy = (double) (ab.getRoyalty()*.01);
						runningTotal+=roy;
						row2 = sheet.createRow(index);
						cell = row2.createCell(2);
						cell.setCellValue(auth.getFirstname()+ " " + auth.getLastname());
						cell = row2.createCell(3);
						cell.setCellValue(roy+"%");
						index++;
						authIndex++;
					}
				}
				authIndex = 0;
				row2 = sheet.createRow(index);
				cell = row2.createCell(2);
				cell.setCellStyle(style);
				cell.setCellValue("Total Royalty");
				cell = row2.createCell(3);
				cell.setCellStyle(style);
				cell.setCellValue(runningTotal+"%");
				index+=2;
				runningTotal = 0;
			}
		}

        
        try
        {
        	JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    		jfc.setDialogTitle("Choose a directory to save your file: ");
    		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    		int returnValue = jfc.showSaveDialog(null);
    		if (returnValue == JFileChooser.APPROVE_OPTION) {
    			if (jfc.getSelectedFile().isDirectory()) {
    				logger.info("Publisher Report saved to " + jfc.getSelectedFile());
    			}
    		}

        	
            FileOutputStream out2 = new FileOutputStream(new File(jfc.getSelectedFile() + "\\"+ pub +"Report.xlsx"));
            workbook.write(out2);
            out2.close();
            index = 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

	private void setButtonHandlers() {
		EventHandler<MouseEvent> submitHandler = new EventHandler<MouseEvent>() {
			// EventHandler has a "handle" function that must be overridden
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					pub = publishers.getValue();
					sheet = workbook.createSheet(pub.toString());
					books = gateway.getBooksForPublisher(pub);
					generateExcel(pub, books);
				}
			}
		};
		submit.setOnMouseClicked(submitHandler);
	}

	private void setFields() {
		publishers.setItems(publisher);
		
	}
}
