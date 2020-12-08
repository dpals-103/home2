package ssg.container;

public class Session {
	public int isSelectBoardId;
	
	public Session(){
		isSelectBoardId = 0; 
	}

	public void selectedBoard(int id) {
		isSelectBoardId = id; 	
	}
	
	
}
