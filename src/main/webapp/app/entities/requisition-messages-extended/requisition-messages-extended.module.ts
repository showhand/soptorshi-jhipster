import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    RequisitionMessagesExtendedComponent,
    RequisitionMessagesExtendedDetailComponent,
    RequisitionMessagesExtendedUpdateComponent,
    requisitionMessagesExtendedRoute,
    requisitionMessagesExtendedPopupRoute
} from './';
import {
    RequisitionMessagesComponent,
    RequisitionMessagesDeleteDialogComponent,
    RequisitionMessagesDeletePopupComponent,
    RequisitionMessagesDetailComponent,
    requisitionMessagesPopupRoute,
    RequisitionMessagesUpdateComponent
} from 'app/entities/requisition-messages';

const ENTITY_STATES = [...requisitionMessagesExtendedRoute, ...requisitionMessagesExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RequisitionMessagesComponent,
        RequisitionMessagesDetailComponent,
        RequisitionMessagesUpdateComponent,
        RequisitionMessagesExtendedComponent,
        RequisitionMessagesExtendedDetailComponent,
        RequisitionMessagesExtendedUpdateComponent,
        RequisitionMessagesDeleteDialogComponent,
        RequisitionMessagesDeletePopupComponent
    ],
    entryComponents: [
        RequisitionMessagesExtendedComponent,
        RequisitionMessagesExtendedUpdateComponent,
        RequisitionMessagesDeleteDialogComponent,
        RequisitionMessagesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionMessagesModule {}
