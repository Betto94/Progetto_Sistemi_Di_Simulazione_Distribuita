package it.uniroma2.cap.federate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import hla.rti1516.jlc.HLAfloat32BE;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.FederateHandleSaveStatusPair;
import hla.rti1516e.FederateHandleSet;
import hla.rti1516e.FederateRestoreStatus;
import hla.rti1516e.FederationExecutionInformationSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.MessageRetractionHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RestoreFailureReason;
import hla.rti1516e.SaveFailureReason;
import hla.rti1516e.SynchronizationPointFailureReason;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.encoding.DataElement;
import hla.rti1516e.encoding.DataElementFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.HLAfloat64BE;
import hla.rti1516e.encoding.HLAinteger64BE;
import hla.rti1516e.encoding.HLAunicodeString;
import hla.rti1516e.encoding.HLAvariableArray;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.time.HLAinteger64Time;
import it.uniroma2.cap.events.EventType;
import it.uniroma2.cap.events.EventoLocaleA;


public class FederateAmbassadorImplA extends NullFederateAmbassador {
	
	//reference to the federate
	private ImpiantoA federate;
	
	// these variables are accessible in the package
	protected long federateTime        = 1; 		//logical time
	protected long federateLookahead   = 1;
		
	protected boolean isRegulating       = false;
	protected boolean isConstrained      = false;

	protected boolean isAdvancing        = false;
		
	protected boolean isAnnounced        = false; 	//check if sync point has been announced
	protected boolean isReadyToRun       = false; 	//check if sync point has been reached
	

	
	public FederateAmbassadorImplA(ImpiantoA fed) {
		this.federate = fed;
	}
	
	/**
	 * @return the federateTime
	 */
	public long getFederateTime() {
		return federateTime;
	}

	/**
	 * @param federateTime the federateTime to set
	 */
	public void setFederateTime(long federateTime) {
		this.federateTime = federateTime;
	}
	
	
	@Override
	public void connectionLost(String faultDescription) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.connectionLost(faultDescription);
	}

	@Override
	public void synchronizationPointRegistrationSucceeded(String synchronizationPointLabel)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		System.out.println("**FedAmb: SYNC Point Registration Succeeded:  " + synchronizationPointLabel);
				
	}

	@Override
	public void synchronizationPointRegistrationFailed(String synchronizationPointLabel,
			SynchronizationPointFailureReason reason) throws FederateInternalError {
		System.out.println("**FedAmb: SYNC Point Registration Failed:  " + synchronizationPointLabel + reason);
	}

	@Override
	public void announceSynchronizationPoint(String synchronizationPointLabel, byte[] userSuppliedTag)
			throws FederateInternalError {	
		System.out.println("**FedAmb: SYNC Point announced:  " + synchronizationPointLabel);
		this.isAnnounced = true;
	}

	@Override
	public void federationSynchronized(String synchronizationPointLabel, FederateHandleSet failedToSyncSet)
			throws FederateInternalError {
		if (synchronizationPointLabel.equals("ReadyToRun")) {
			this.isReadyToRun = true;
		}
		System.out.println("**FedAmb: Federation synchronized at sync point: "+synchronizationPointLabel);
	}

	@Override
	public void initiateFederateSave(String label) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.initiateFederateSave(label);
	}

	@Override
	public void initiateFederateSave(String label, LogicalTime time) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.initiateFederateSave(label, time);
	}

	@Override
	public void federationSaved() throws FederateInternalError {
		// TODO Auto-generated method stub
		super.federationSaved();
	}

	@Override
	public void federationNotSaved(SaveFailureReason reason) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.federationNotSaved(reason);
	}

	@Override
	public void federationSaveStatusResponse(FederateHandleSaveStatusPair[] response) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.federationSaveStatusResponse(response);
	}

	@Override
	public void requestFederationRestoreSucceeded(String label) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.requestFederationRestoreSucceeded(label);
	}

	@Override
	public void requestFederationRestoreFailed(String label) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.requestFederationRestoreFailed(label);
	}

	@Override
	public void federationRestoreBegun() throws FederateInternalError {
		// TODO Auto-generated method stub
		super.federationRestoreBegun();
	}

	@Override
	public void initiateFederateRestore(String label, String federateName, FederateHandle federateHandle)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.initiateFederateRestore(label, federateName, federateHandle);
	}

	@Override
	public void federationRestored() throws FederateInternalError {
		// TODO Auto-generated method stub
		super.federationRestored();
	}

	@Override
	public void federationNotRestored(RestoreFailureReason reason) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.federationNotRestored(reason);
	}

	@Override
	public void federationRestoreStatusResponse(FederateRestoreStatus[] response) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.federationRestoreStatusResponse(response);
	}

	@Override
	public void reportFederationExecutions(FederationExecutionInformationSet theFederationExecutionInformationSet)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.reportFederationExecutions(theFederationExecutionInformationSet);
	}

	@Override
	public void startRegistrationForObjectClass(ObjectClassHandle theClass) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.startRegistrationForObjectClass(theClass);
	}

	@Override
	public void stopRegistrationForObjectClass(ObjectClassHandle theClass) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.stopRegistrationForObjectClass(theClass);
	}

	@Override
	public void turnInteractionsOn(InteractionClassHandle theHandle) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.turnInteractionsOn(theHandle);
	}

	@Override
	public void turnInteractionsOff(InteractionClassHandle theHandle) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.turnInteractionsOff(theHandle);
	}

	@Override
	public void objectInstanceNameReservationSucceeded(String objectName) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.objectInstanceNameReservationSucceeded(objectName);
	}

	@Override
	public void multipleObjectInstanceNameReservationSucceeded(Set<String> objectNames) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.multipleObjectInstanceNameReservationSucceeded(objectNames);
	}

	@Override
	public void objectInstanceNameReservationFailed(String objectName) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.objectInstanceNameReservationFailed(objectName);
	}

	@Override
	public void multipleObjectInstanceNameReservationFailed(Set<String> objectNames) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.multipleObjectInstanceNameReservationFailed(objectNames);
	}

	@Override
	public void discoverObjectInstance(ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass,
			String objectName, FederateHandle producingFederate) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.discoverObjectInstance(theObject, theObjectClass, objectName, producingFederate);
	}

	

	@Override
	public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters,
			byte[] userSuppliedTag, OrderType sentOrdering, TransportationTypeHandle theTransport,
			SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
		System.out.println("**FedAmb: Interaction Received:  ");
		super.receiveInteraction(interactionClass, theParameters,
				userSuppliedTag, sentOrdering, theTransport,
				 receiveInfo);
	}

	@Override
	public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters,
			byte[] userSuppliedTag, OrderType sentOrdering, TransportationTypeHandle theTransport, LogicalTime theTime,
			OrderType receivedOrdering, SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
		;
		try{
			//decodifica del tipo dell'evento ricevuto
			HLAunicodeString typeEncoder = federate.encoderFactory.createHLAunicodeString();		
			typeEncoder.decode(theParameters.get(federate.typeHandleM));
	        String type = typeEncoder.getValue();
	        //tempo dell'evento
	        long eventTime = ((HLAinteger64Time)theTime).getValue();
	        switch(type) {
	        case "DELIVERY_COMPONENT":
	        	    System.out.println("[" + this.federateTime + "] " + this.federate.federateName +"_FEDAMB Received Interaction. Create the following Local Event");
	        	    //decodifica del pezzo inviato da ImpiantoM
	        	    HLAunicodeString pezzoDecoder = federate.encoderFactory.createHLAunicodeString();
	        	    pezzoDecoder.decode(theParameters.get(federate.pezzoHandle));
		        	String pezzo = pezzoDecoder.getValue();
		        	if (eventTime <= federateTime)
		        		eventTime = federateTime +1;
		        	//aggiunge alla lista degli eventi di A l'evento VEICHLE ASSEMBlATION contenente il pezzo ricevuto da ImpiantoM 
		        	federate.addEvent(new EventoLocaleA(EventType.VEHICLE_ASSEMBLATION, eventTime, pezzo));
		        	System.out.println("\t interaction timestamp: " + eventTime);
		            System.out.println("\t tipo pezzo: " + pezzo);
		            System.out.println("\t type: " + type);
	        	break;
	        	
	        case "SEND_REPORT":
	        	    System.out.println("[" + this.federateTime + "] " + this.federate.federateName +"_FEDAMB Received Interaction. Create the following Local Event");
	        	    //decodifica del report inviato da ImpiantoM
	        	    DataElementFactory factory = new DataElementFactory(){
	    			    public DataElement createElement(int index)
	    			    {
	    			       return federate.encoderFactory.createHLAfloat64BE();
	    			    }
	    			 };
	    			ArrayList<Float> reportM = new ArrayList<Float>();
	    			HLAvariableArray reportDecoder = federate.encoderFactory.createHLAvariableArray(factory);
	        		reportDecoder.decode(theParameters.get(federate.reportHandle));
	        		Iterator i = reportDecoder.iterator();
	        		HLAfloat64BE rDecoder;
	        		while(i.hasNext()) {
	        			rDecoder = (HLAfloat64BE)i.next();
	        			
	        			reportM.add((float)rDecoder.getValue());
	        		}
	        		if (eventTime <= federateTime)
		        		eventTime = federateTime +1;
	        		//aggiunge alla lista degli eventi di A l'evento RECEIVE REPORT M contenente il report ricevuto da ImpiantoM 
		        	federate.addEvent(new EventoLocaleA(EventType.RECEIVE_REPORT_M, eventTime, reportM));
		        	System.out.println("\t interaction timestamp: " + eventTime);
		            System.out.println("\t report di M: " + reportM);
		            System.out.println("\t type: " + type);
	        	break;
        }
        	
        }catch (DecoderException e) {
            System.out.println("Failed to decode incoming interaction");
        }
   
        
	
	}

	@Override
	public void receiveInteraction(InteractionClassHandle interactionClass, 
								   ParameterHandleValueMap theParameters,
								   byte[] userSuppliedTag, 
								   OrderType sentOrdering, 
								   TransportationTypeHandle theTransport, 
								   LogicalTime theTime,
								   OrderType receivedOrdering, 
								   MessageRetractionHandle retractionHandle, 
								   SupplementalReceiveInfo receiveInfo)
			throws FederateInternalError {
		
		System.out.println("**FedAmb: Interaction Received:  ");
		super.receiveInteraction(interactionClass, theParameters,
				userSuppliedTag, sentOrdering, theTransport,theTime,receivedOrdering,retractionHandle,
				 receiveInfo);
				
	}

	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] userSuppliedTag, OrderType sentOrdering,
			SupplementalRemoveInfo removeInfo) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, removeInfo);
	}

	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] userSuppliedTag, OrderType sentOrdering,
			LogicalTime theTime, OrderType receivedOrdering, SupplementalRemoveInfo removeInfo)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, theTime, receivedOrdering, removeInfo);
	}

	@Override
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] userSuppliedTag, OrderType sentOrdering,
			LogicalTime theTime, OrderType receivedOrdering, MessageRetractionHandle retractionHandle,
			SupplementalRemoveInfo removeInfo) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, theTime, receivedOrdering, retractionHandle,
				removeInfo);
	}

	@Override
	public void attributesInScope(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.attributesInScope(theObject, theAttributes);
	}

	@Override
	public void attributesOutOfScope(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.attributesOutOfScope(theObject, theAttributes);
	}

	@Override
	public void provideAttributeValueUpdate(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
			byte[] userSuppliedTag) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.provideAttributeValueUpdate(theObject, theAttributes, userSuppliedTag);
	}

	@Override
	public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.turnUpdatesOnForObjectInstance(theObject, theAttributes);
	}

	@Override
	public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
			String updateRateDesignator) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.turnUpdatesOnForObjectInstance(theObject, theAttributes, updateRateDesignator);
	}

	@Override
	public void turnUpdatesOffForObjectInstance(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.turnUpdatesOffForObjectInstance(theObject, theAttributes);
	}

	@Override
	public void confirmAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
			AttributeHandleSet theAttributes, TransportationTypeHandle theTransportation) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.confirmAttributeTransportationTypeChange(theObject, theAttributes, theTransportation);
	}

	@Override
	public void confirmInteractionTransportationTypeChange(InteractionClassHandle theInteraction,
			TransportationTypeHandle theTransportation) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.confirmInteractionTransportationTypeChange(theInteraction, theTransportation);
	}

	@Override
	public void reportAttributeTransportationType(ObjectInstanceHandle theObject, AttributeHandle theAttribute,
			TransportationTypeHandle theTransportation) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.reportAttributeTransportationType(theObject, theAttribute, theTransportation);
	}

	@Override
	public void reportInteractionTransportationType(FederateHandle theFederate, InteractionClassHandle theInteraction,
			TransportationTypeHandle theTransportation) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.reportInteractionTransportationType(theFederate, theInteraction, theTransportation);
	}

	@Override
	public void requestAttributeOwnershipAssumption(ObjectInstanceHandle theObject,
			AttributeHandleSet offeredAttributes, byte[] userSuppliedTag) throws FederateInternalError {
		// TODO Auto-generated method stub
		System.out.println("**FedAmb: Attribute Ownership Requested ");
		super.requestAttributeOwnershipAssumption(theObject, offeredAttributes, userSuppliedTag);
	}

	@Override
	public void requestDivestitureConfirmation(ObjectInstanceHandle theObject, AttributeHandleSet offeredAttributes)
			throws FederateInternalError {
		System.out.println("**FedAmb: Divest Confirmation ");
		// TODO Auto-generated method stub
		super.requestDivestitureConfirmation(theObject, offeredAttributes);
	}


	@Override
	public void attributeOwnershipUnavailable(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.attributeOwnershipUnavailable(theObject, theAttributes);
	}

	@Override
	public void requestAttributeOwnershipRelease(ObjectInstanceHandle theObject, AttributeHandleSet candidateAttributes,
			byte[] userSuppliedTag) throws FederateInternalError {
		// TODO Auto-generated method stub
		//super.requestAttributeOwnershipRelease(theObject, candidateAttributes, userSuppliedTag);

		System.out.println("****FedAmb: Received Attribute Ownership Acquisition Request -> Confirmed");
		try {
			this.federate.rtiAmb.attributeOwnershipDivestitureIfWanted(theObject, candidateAttributes);
		}
		catch(Exception e) {System.out.println("****FedAmb: Error in handling attribute divesting \n"); e.printStackTrace();}
	}

	@Override
	public void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle theObject,
			AttributeHandleSet theAttributes) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.confirmAttributeOwnershipAcquisitionCancellation(theObject, theAttributes);
	}

	@Override
	public void informAttributeOwnership(ObjectInstanceHandle theObject, AttributeHandle theAttribute,
			FederateHandle theOwner) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.informAttributeOwnership(theObject, theAttribute, theOwner);
	}

	@Override
	public void attributeIsNotOwned(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.attributeIsNotOwned(theObject, theAttribute);
	}

	@Override
	public void attributeIsOwnedByRTI(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
			throws FederateInternalError {
		// TODO Auto-generated method stub
		super.attributeIsOwnedByRTI(theObject, theAttribute);
	}


	@Override
	public void timeConstrainedEnabled(LogicalTime time) throws FederateInternalError {
		// TODO Auto-generated method stub
		//super.timeConstrainedEnabled(time);
		System.out.println("****FedAmb: Time Constrained Enabled");
		this.isConstrained = true;
	}
	@Override
	public void timeRegulationEnabled(LogicalTime time) throws FederateInternalError {
		// TODO Auto-generated method stub
		System.out.println("****FedAmb: Time Regulation Enabled");
		this.isRegulating = true;
	}

	@Override
	public void timeAdvanceGrant(LogicalTime theTime) throws FederateInternalError {
		federateTime = ((HLAinteger64Time)theTime).getValue();
		federateTime = ((HLAinteger64Time)theTime).getValue();
		this.isAdvancing = false;
		
	}

	@Override
	public void requestRetraction(MessageRetractionHandle theHandle) throws FederateInternalError {
		// TODO Auto-generated method stub
		super.requestRetraction(theHandle);
	}
	
	

}
