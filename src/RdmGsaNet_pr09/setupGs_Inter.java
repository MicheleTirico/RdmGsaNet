package RdmGsaNet_pr09;

public interface setupGs_Inter {
	
	// costants and enum
	enum gsGridType { grid4 , grid8 , grid8weig }
	
	enum disMorpType { homo, random }
	
	// METHODS
	void createLayerGs() ;
	
	void setCoordinate() ; // not used in setup layer GRID, it may be important in GIS setup
	
// PRIVATE METHODS  ---------------------------------------------------------------------------------------------------------------------------------
	

}
