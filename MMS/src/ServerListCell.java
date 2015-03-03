import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//@author A0096724W
public class ServerListCell extends ListCell<Server>{
	private final String ICONVIEWER = "\uf1e5";
	private final String ICONSTREAMER = "\uf21a";
	private final String CSS_SERVERLISTPANEL = "serverListPanel";
	private final String CSS_FONTSIZE20 = "font20";
	private final String CSS_FONTAWESOME12 = "fontawesome12";
	
	private final int COLUMN_0 = 10; 
	private final int COLUMN_1 = 15; 
	private final int COLUMN_2 = 60; 
	private final int COLUMN_3 = 15; 
	private final int COLUMN_4 = 60; 
	private final int COLUMN_5 = 50; 
	private final int ROW_0 = 48; 
	
	private final int HORIZONTALGAP = 5;
	private final int TOPPADDING = 0;
	private final int RIGHTPADDING = 0;
	private final int BOTTOMPADDING = 5;
	private final int LEFTPADDING = 0;
	
	private final int COLUMNINDEX_SERVERNAME = 1; 
	private final int ROWINDEX_SERVERNAME = 0;
	private final int COLUMNSPAN_SERVERNAME = 4;
	private final int ROWSPAN_SERVERNAME = 1;
	
	private final int COLUMNINDEX_ICONVIEWER = 1; 
	private final int ROWINDEX_ICONVIEWER = 1;
	private final int COLUMNSPAN_ICONVIEWER = 1;
	private final int ROWSPAN_ICONVIEWER = 1;
	
	private final int COLUMNINDEX_ICONSTREAMER = 3; 
	private final int ROWINDEX_ICONSTREAMER = 1;
	private final int COLUMNSPAN_ICONSTREAMER = 1;
	private final int ROWSPAN_ICONSTREAMER = 1;
	
	private final int COLUMNINDEX_VIEWERCOUNT = 2; 
	private final int ROWINDEX_VIEWERCOUNT = 1;
	private final int COLUMNSPAN_VIEWERCOUNT = 1;
	private final int ROWSPAN_VIEWERCOUNT = 1;
	
	private final int COLUMNINDEX_STREAMERCOUNT = 4; 
	private final int ROWINDEX_STREAMERCOUNT = 1;
	private final int COLUMNSPAN_STREAMERCOUNT = 1;
	private final int ROWSPAN_STREAMERCOUNT= 1;
	
	private GridPane _grid = new GridPane();
	private Label _iconViewer = new Label();
	private Label _iconStreamer = new Label();
	private Label _serverName = new Label();
	private Label _viewerCount = new Label();
	private Label _streamerCount = new Label();
	
	public ServerListCell() {
		configureGrid();
		configureAllIcons();
		configureServerName();
		configureServerViewers();
		configureServerStreamers();
		addControlsToGrid(); 
	}

	private void configureGrid() {
		_grid.setHgap(HORIZONTALGAP);
		_grid.setPadding(new Insets(TOPPADDING, RIGHTPADDING, BOTTOMPADDING, LEFTPADDING));
		
		_grid.getColumnConstraints().add(new ColumnConstraints(COLUMN_0));
		_grid.getColumnConstraints().add(new ColumnConstraints(COLUMN_1));
		_grid.getColumnConstraints().add(new ColumnConstraints(COLUMN_2));
		_grid.getColumnConstraints().add(new ColumnConstraints(COLUMN_3));
		_grid.getColumnConstraints().add(new ColumnConstraints(COLUMN_4));
		_grid.getColumnConstraints().add(new ColumnConstraints(COLUMN_5));
		
		_grid.getRowConstraints().add(new RowConstraints(ROW_0));

		_grid.setHalignment(_iconViewer, HPos.CENTER);
		_grid.setHalignment(_iconStreamer, HPos.CENTER);
		
		_grid.getStyleClass().add(CSS_SERVERLISTPANEL);
		//_grid.setGridLinesVisible(true); //for debugging GUI
	}
	
	private void configureServerViewers() {
		
	}

	private void configureServerName() {
		_serverName.getStyleClass().add(CSS_FONTSIZE20);
	}
	
	private void configureServerStreamers() {
		
		
	}

	private void configureAllIcons() {
		_iconViewer.getStyleClass().add(CSS_FONTAWESOME12);
		_iconStreamer.getStyleClass().add(CSS_FONTAWESOME12);
	}


	private void addControlsToGrid() {
		_grid.add(_serverName, COLUMNINDEX_SERVERNAME, ROWINDEX_SERVERNAME, COLUMNSPAN_SERVERNAME, ROWSPAN_SERVERNAME);
		_grid.add(_iconViewer, COLUMNINDEX_ICONVIEWER, ROWINDEX_ICONVIEWER, COLUMNSPAN_ICONVIEWER, ROWSPAN_ICONVIEWER);
		_grid.add(_iconStreamer, COLUMNINDEX_ICONSTREAMER, ROWINDEX_ICONSTREAMER, COLUMNSPAN_ICONSTREAMER, ROWSPAN_ICONSTREAMER);
		_grid.add(_viewerCount, COLUMNINDEX_VIEWERCOUNT, ROWINDEX_VIEWERCOUNT, COLUMNSPAN_VIEWERCOUNT, ROWSPAN_VIEWERCOUNT);
		_grid.add(_streamerCount, COLUMNINDEX_STREAMERCOUNT, ROWINDEX_STREAMERCOUNT, COLUMNSPAN_STREAMERCOUNT, ROWSPAN_STREAMERCOUNT);
	}

	@Override
	public void updateItem(Server server, boolean empty) {
		super.updateItem(server, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			addContent(server);
		}
	}

	private void addContent(Server server){
		setServerName(server);
		setViewerCount(server);
		setStreamerCount(server);
		setAllIcons(server);
		setText(null);
		setGraphic(_grid);
	}

	private void setStreamerCount(Server server) {
		String streamerCount = server.getStreamerCount() + "";
		_streamerCount.setText(streamerCount);
	}

	private void setViewerCount(Server server) {
		String viewerCount = server.getViewerCount() + "";
		_viewerCount.setText(viewerCount);
	}

	private void setServerName(Server server) {
		String serverName = server.getServerName();
		_serverName.setText(serverName);
	}
	
	private void setAllIcons(Server server) {
		_iconViewer.setText(ICONVIEWER);
		_iconStreamer.setText(ICONSTREAMER);
	}
}
