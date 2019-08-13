import { requisitionExtendedPopupRoute, requisitionExtendedRoute } from 'app/entities/requisition-extended';
import { requisitionDetailsExtendedPopupRoute, requisitionDetailsExtendedRoute } from 'app/entities/requisition-details-extended';

export const ENTITY_STATES = [
    ...requisitionExtendedRoute,
    ...requisitionExtendedPopupRoute,
    ...requisitionDetailsExtendedRoute,
    ...requisitionDetailsExtendedPopupRoute
];
