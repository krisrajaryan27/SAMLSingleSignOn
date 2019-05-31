package com.talentPool.notifier.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.notifier.OutBoundConstants;
import com.talentPool.notifier.dataobject.OutBoundData;

public class OutBoundManager {
	private void insertOutBound(OutBoundData data) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOutBoundManager_Insert");
			dq.setString(1, OutBoundConstants.SENT_STATUS_WAIT);
			dq.setString(2, data.getSendTo());
			dq.setString(3, data.getSendFrom());
			dq.setString(4, data.getOutboundType());
			dq.setString(5, data.getOutboundMode());
			dq.setString(6, data.getEntityId());
			dq.setString(7, data.getEntityType());
			dq.setString(8, data.getUserId());
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting outbound object", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

	}

	public void insertNotification(String ids, String templateCode, String userId, String entityType, String mode, String sendFrom) {
		try {
			if (!Utils.isBlankOrNull(ids)) {
				String[] entityIds = ids.split(",");
				if (entityIds != null) {
					for (int i = 0; i < entityIds.length; i++) {
						String entityId = entityIds[i];
						OutBoundData outboundData = new OutBoundData();
						outboundData.setOutboundType(templateCode);
						outboundData.setUserId(userId);
						outboundData.setOutboundMode(mode);
						outboundData.setEntityType(entityType);
						outboundData.setEntityId(entityId);
						outboundData.setSendFrom(sendFrom);
						insertOutBound(outboundData);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting notification", e);
		}
	}

	public ArrayList getAllNotifications(String mode, String status, int batchSize) {
		ArrayList results = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOutBoundManager_GetNotifications");
			dq.setString(1, mode);
			dq.setString(2, status);
			dq.setInt(3, batchSize);
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting outbound object", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return results;
	}

	public void update(OutBoundData outboundData) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOutBoundManager_UpdateOutbound");
			dq.setString(1, outboundData.getSentStatus());
			dq.setString(2, outboundData.getSentMessageId());
			dq.setInt(3, outboundData.getOutboundId());
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating outbound object", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

	}

	public void setMessageData(int outboundId, MessageData messageData) {
		DBPreparedQuery dq = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(messageData);
			byte[] bytes = byteArrayOutputStream.toByteArray();

			dq = new DBPreparedQuery("dOutBoundManager_UpdateMessageData");
			dq.setBytes(1, bytes);
			dq.setInt(2, outboundId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating outbound object for message", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public MessageData getMessageData(int outboundId) {
		DBPreparedQuery dq = null;
		MessageData messageData = null;
		try {
			dq = new DBPreparedQuery("dOutBoundManager_GetMessageData");
			dq.setInt(1, outboundId);
			SimpleDataObject entity = (SimpleDataObject) dq.getSingleObjectResult();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[]) entity.getAttribute("dataObject"));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			messageData = (MessageData) objectInputStream.readObject();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating outbound object for message", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return messageData;
	}
}
