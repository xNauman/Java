package route;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class SchRouteResp {

	//Responses from NAV
	private static Map<UUID, SchRouteMoveReqResp> mNavResp = new HashMap<UUID, SchRouteMoveReqResp>();
	
	public static boolean addResponseFromNav(SchRouteMoveReqResp pResp){
		if (mNavResp.containsKey(pResp.getRequestUUID()))
			return false;
		
		mNavResp.put(pResp.getRequestUUID(), pResp);
		
		return true;
	}
	
	public static boolean removeResponseFromNav(UUID pRespUUID){
		
		if (mNavResp.containsKey(pRespUUID)) {
			mNavResp.remove(pRespUUID);
			return true;
		}
		
		return false;			
	}
	
	public static boolean containsResponse(UUID pRespUUID){
		return mNavResp.containsKey(pRespUUID);
	}
	
	public static SchRouteMoveReqResp getResponse(UUID pRespUUID, boolean bRemoveResponse){
		if (containsResponse(pRespUUID)) {
			SchRouteMoveReqResp resp = mNavResp.get(pRespUUID);
			if (bRemoveResponse) mNavResp.remove(pRespUUID);
			return resp;
		}
		
		return null;
	}
	
	public static void clearAll(){
		mNavResp.clear();
	}
	
	public static int getCount(){
		return mNavResp.size();
	}
	
	public static void debugListAll(){
		Iterable<Entry<UUID,SchRouteMoveReqResp>> contents = mNavResp.entrySet();
		
		Iterator<Entry<UUID, SchRouteMoveReqResp>> itr = contents.iterator();
		
		System.out.println("Listing of Responses from NAV, but not yet processed.");
	
		while (itr.hasNext()){
			Map.Entry<UUID, SchRouteMoveReqResp> pair = (Map.Entry<UUID, SchRouteMoveReqResp>) itr.next();
			SchRouteMoveReqResp r = (SchRouteMoveReqResp) pair.getValue();
			
			System.out.println("  " + r.getRequestUUID().toString().toUpperCase() + " = " + r.getResponse() + ", Received: " + util.Formatter.formatDate(r.getResponseReceivedTime()));
		}
		
		System.out.println("Count = " + mNavResp.size());
	}
}
