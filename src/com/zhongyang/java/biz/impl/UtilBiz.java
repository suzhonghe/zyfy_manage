package com.zhongyang.java.biz.impl;

import com.zhongyang.java.system.ZSession;
import com.zhongyang.java.vo.ProjectTransfer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 2015/12/29.
 */
public abstract class UtilBiz {
        public Map<String,String> getProjectTransfer(ProjectTransfer projectTransfer){
            Map<String,String> project=new HashMap<>();
            if (projectTransfer!=null){
                project.put("order_id", projectTransfer.getOrder_id());
                project.put("mer_date", projectTransfer.getMer_date());
                project.put("project_id",projectTransfer.getProject_id());
                project.put("serv_type",projectTransfer.getServ_type());
                project.put("trans_action",projectTransfer.getTrans_action());
                project.put("partic_type",projectTransfer.getPartic_type());
                project.put("partic_acc_type",projectTransfer.getPartic_acc_type());
                project.put("partic_user_id",projectTransfer.getPartic_user_id());
                project.put("partic_account_id",projectTransfer.getPartic_account_id());
                project.put("notify_url",projectTransfer.getNotify_url());
                project.put("amount",String.valueOf(projectTransfer.getAmount()));
            }
            return project;
        }
        
        public Map<String,String> getProjectFeeTransfer(ProjectTransfer projectTransfer){
            Map<String,String> project=new HashMap<>();
            if (projectTransfer!=null){
                project.put("order_id", projectTransfer.getOrder_id());
                project.put("mer_date", projectTransfer.getMer_date());
                project.put("project_id",projectTransfer.getProject_id());
                project.put("serv_type",projectTransfer.getServ_type());
                project.put("trans_action",projectTransfer.getTrans_action());
                project.put("partic_type",projectTransfer.getPartic_type());
                project.put("partic_acc_type",projectTransfer.getPartic_acc_type());
                project.put("partic_user_id",projectTransfer.getPartic_user_id());
              //  project.put("partic_account_id",projectTransfer.getPartic_account_id());
             //   project.put("ret_url",projectTransfer.getRet_url());
                project.put("notify_url",projectTransfer.getNotify_url());
                project.put("amount",String.valueOf(projectTransfer.getAmount()));
            }
            return project;
        }

        public  void setZsession(Object object,String uuid){
            ZSession zsession = ZSession.getzSession();
            zsession.getzSessionMap().put(uuid,object);
        }

         public  Object getZsession(String uuid){
            if (uuid!=null){
            ZSession zsession = ZSession.getzSession();
            Map<String,Object> sessionMap= (Map<String, Object>) zsession.getzSessionMap().get(uuid);
            zsession.getzSessionMap().remove(uuid);
             return sessionMap;
             }
             return null;
        }
         
         public Object getZsessionObject(String uuid){
        	 if (uuid!=null){
                 ZSession zsession = ZSession.getzSession();
                 Map<String,Object> sessionMap= (Map<String, Object>) zsession.getzSessionMap();
              //   zsession.getzSessionMap().remove(uuid);
                  return sessionMap.get(uuid);
                  }
                  return null;
         }
         
         public boolean removeZsessionObject(String uuid){
        	 if (uuid!=null){
                 ZSession zsession = ZSession.getzSession();
                 Map<String,Object> sessionMap= (Map<String, Object>) zsession.getzSessionMap();
              //   zsession.getzSessionMap().remove(uuid);
                   sessionMap.remove(uuid);
                   return true;
                  }
        	 return false;
         }
}
