package com.smartjava.demo.response;

public class ResultResponse {
	
    private MetaDataResponse metadata;
    private Object result;
    
    
	public MetaDataResponse getMetadata() {
		return metadata;
	}
	public void setMetadata(MetaDataResponse metadata) {
		this.metadata = metadata;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
    
    
    

}
