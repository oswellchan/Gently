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
	private static final String ICONVIEWER = "\uf1e5";
	private static final String ICONSTREAMER = "\uf21a";

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
		_grid.setHgap(5);
		_grid.setPadding(new Insets(0, 0, 5, 0));
		
		_grid.getColumnConstraints().add(new ColumnConstraints(10)); // column 0 
		_grid.getColumnConstraints().add(new ColumnConstraints(15)); // column 0 
		_grid.getColumnConstraints().add(new ColumnConstraints(60)); // column 1 
		_grid.getColumnConstraints().add(new ColumnConstraints(15)); // column 2
		_grid.getColumnConstraints().add(new ColumnConstraints(60)); // column 3
		_grid.getColumnConstraints().add(new ColumnConstraints(50)); // column 4

		_grid.getRowConstraints().add(new RowConstraints(48));

		_grid.setHalignment(_iconViewer, HPos.CENTER);
		_grid.setHalignment(_iconStreamer, HPos.CENTER);
		
		_grid.getStyleClass().add("serverListPanel");
		//_grid.setGridLinesVisible(true);
	}
	
	private void configureServerViewers() {
		
	}

	private void configureServerName() {
		_serverName.getStyleClass().add("font20");
	}
	
	private void configureServerStreamers() {
		
		
	}

	private void configureAllIcons() {
		_iconViewer.getStyleClass().add("fontawesome12");
		_iconStreamer.getStyleClass().add("fontawesome12");
	}


	private void addControlsToGrid() {
		_grid.add(_serverName, 1, 0, 4, 1);
		_grid.add(_iconViewer, 1, 1, 1, 1);
		_grid.add(_iconStreamer, 3, 1, 1, 1);
		_grid.add(_viewerCount, 2, 1, 1, 1);
		_grid.add(_streamerCount, 4, 1, 1, 1);
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
