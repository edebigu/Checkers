package es.ericsson.masterCraftmanship.tfm.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;

@EntityScan
public class MoveDto {
	
	private String originRow;
	private String originCol;
	private String targetRow;
	private String targetCol;
	
	public MoveDto() {
		
	}

	public MoveDto(String originRow, String originCol, String targetRow, String targetCol) {
		this.originRow = originRow;
		this.originCol = originCol;
		this.targetRow = targetRow;
		this.targetCol = targetCol;
	}

	public String getOriginRow() {
		return originRow;
	}

	public void setOriginRow(String originRow) {
		this.originRow = originRow;
	}

	public String getOriginCol() {
		return originCol;
	}

	public void setOriginCol(String originCol) {
		this.originCol = originCol;
	}

	public String getTargetRow() {
		return targetRow;
	}

	public void setTargetRow(String targetRow) {
		this.targetRow = targetRow;
	}

	public String getTargetCol() {
		return targetCol;
	}

	public void setTargetCol(String targetCol) {
		this.targetCol = targetCol;
	}
	
	public void validate() {
		if (originRow == null || originRow.isEmpty() || originCol == null || originCol.isEmpty()
			||	targetRow == null || targetRow.isEmpty() || targetCol == null || targetCol.isEmpty()) {
			throw new BadRequestException ("Incomplete MoveDto");
		}
	}
	
	

}
