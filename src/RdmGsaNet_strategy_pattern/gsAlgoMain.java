package RdmGsaNet_strategy_pattern;







public class gsAlgoMain {
	
	private enum inter { reaction, diffusion }
	
	private gsAlgo inter;
	
	public gsAlgoMain (gsAlgo inter) {
		this.inter = inter;
	}
	
	public void setInter (gsAlgo inter) {
		this.inter = inter ;
	}
	
	public void interaction () {
		inter.gsAlgoExecute();
	}
}
