package com.zzzpro.zzz.sol.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzzpro.zzz.sol.dao.LolmateDao;
import com.zzzpro.zzz.sol.dto.LolmateAPPChatDto;
import com.zzzpro.zzz.sol.dto.LolmateAppDto;
import com.zzzpro.zzz.sol.dto.LolmateDto;


@Service
public class LolmateService {
	
	@Autowired
	private LolmateDao lmDao;

	public HashMap<String, ArrayList<LolmateDto>> lmList(LolmateDto lmDto) {
		System.out.println(lmDto.getLm_gameMate()+","+lmDto.getLm_gameMode()+","+lmDto.getLm_tier()+","+lmDto.getLm_findPosition());
		
		lmDto.setLm_tier("%"+lmDto.getLm_tier()+"%");
		lmDto.setLm_findPosition("%"+lmDto.getLm_findPosition()+"%");
		
		HashMap<String, ArrayList<LolmateDto>> hm = new HashMap<>();
		
		if(lmDto.getLm_tier().equals("%All%") && lmDto.getLm_findPosition().equals("%All%")) {
			hm.put("serch", lmDao.lmMMAllList(lmDto));
		}else if(lmDto.getLm_tier().equals("%All%")) {
			hm.put("serch", lmDao.lmAllTierList(lmDto));
		}else if(lmDto.getLm_findPosition().equals("%All%")) {
			hm.put("serch", lmDao.lmAllPositionList(lmDto));
		}else {
			hm.put("serch", lmDao.lmSerchList(lmDto));
		}
		
		hm.put("all", lmDao.lmAllList());
		return hm;
	}
	
	
	public String lmWrite(LolmateDto lmDto) {
		try {
	        // 파이썬 스크립트 실행을 위한 ProcessBuilder 생성
			System.out.println("파이썬 들어가기 전");
			System.out.println("lm_summonerName"+lmDto.getLm_summonerName());
	        ProcessBuilder pb = new ProcessBuilder("python", "lolmate.py", lmDto.getLm_summonerName());
	        Process p = pb.start();		// 실행
	        
	        //p.waitFor();	// 파이썬 스크립트가 실행되고 있는 동안 대기
	        
	        // 실행 결과를 읽어오기 위해 BufferedReader 사용
	        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        
	        ArrayList<Object> tL = new ArrayList<>();	// 파이썬 스크립트에서 출력한 결과를 저장하기 위한 리스트
	        String ol;		// 파이썬 스크립트에서 출력한 결과를 읽어옴
	        System.out.println("파이썬 다녀옴");
	        while ((ol = br.readLine()) != null) {
	            // 여기서 파이썬 스크립트에서 출력한 결과를 가지고 원하는 작업 수행 (ex; 결과를 가지고 다른 작업을 수행하거나 저장할 수 있음)
	            System.out.println("Python script output: " + ol);
	            tL.add(ol);
	        }
	        
	        if(tL.size()!=0) {
	        	System.out.println("Result: " + tL);
				
				lmDto.setLm_winrate(Double.parseDouble(String.valueOf(tL.get(0))));
				
				String tier = ((String)tL.get(1)).toUpperCase();
				tier = tier.substring(0, 1).toUpperCase() + tier.substring(1);
				lmDto.setLm_tier(tier);
				
	        	System.out.println(lmDto);
	        	
				if(lmDao.lmWrite(lmDto)) { return "ok"; }
				 
	        }else {
	        	System.out.println("Result: 왜 없음");
	        }
	    } catch (IOException /*| InterruptedException*/ e) {
	        e.printStackTrace();
	    }
		return "no";
	}


	public ArrayList<LolmateDto> myLmList(LolmateDto lmDto) {
		ArrayList<LolmateDto> lmList = lmDao.mLList(lmDto);
		for(int i=0; i<lmList.size(); i++) {
			LolmateDto lm = lmList.get(i);
			lm.setLm_app(lmDao.mLAppList(lm));
			lmList.set(i, lm);
		}
		return lmList;
	}


	public ArrayList<LolmateDto> myAppList(String m_id) {
		return lmDao.myAppList(m_id);
	}

	
	public LolmateDto lmDetail(int lm_num) {
		LolmateDto lm = lmDao.lmDetail(lm_num);
		if(lm.getLm_num()!=0) {
			lm.setLm_app(lmDao.mLAppList(lm));
			return lm;
		}
		return null;
	}

	public String myAppDel(int lm_num, String m_id) {
		if(lmDao.myAppDel(lm_num,m_id)) {
			return "ok";
		}
		return "no";
	}


	public String myLmApp(LolmateAppDto lmApp) {
		return lmDao.myLmApp(lmApp);
	}
	
	
	public LolmateDto lmAppList(LolmateDto lm) {
		lm.setLm_app(lmDao.mLAppList(lm));
		ArrayList<LolmateAPPChatDto> lmACList = new ArrayList<>();
		for(int i=0; i<lm.getLm_app().size(); i++) {
			lm.getLm_app().get(i).setLm_num(lm.getLm_num());
			LolmateAPPChatDto lmAC = lmDao.lmAppFinalChatList(lm.getLm_app().get(i));
			lmACList.add(lmAC);
		}
		lm.setLm_app_chat(lmACList);
		return lm;
	}


	public ArrayList<LolmateAPPChatDto> appChatList(int lm_num, String app_name) {
		return lmDao.appChatList(lm_num,app_name);
	}


	public boolean chatAppend(LolmateAPPChatDto lmACDto) {
		if(lmACDto.getRecipient_m_id()=="app") {
			lmACDto.setRecipient_m_id(null);
		}
		if(lmDao.chatAppend(lmACDto)) {
			System.out.println(lmACDto);
			return true;
		}
		return false;
	}


	public boolean close(int lm_num) {
		if(lmDao.close(lm_num)) {
			return true;
		}
		return false;
	}


	public boolean delete(int lm_num) {
		if(lmDao.delete(lm_num)) {
			return true;
		}
		return false;
	}


	
}
