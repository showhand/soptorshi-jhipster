import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    RequisitionMessagesComponent,
    RequisitionMessagesDetailComponent,
    RequisitionMessagesUpdateComponent,
    RequisitionMessagesDeletePopupComponent,
    RequisitionMessagesDeleteDialogComponent,
    requisitionMessagesRoute,
    requisitionMessagesPopupRoute
} from './';

const ENTITY_STATES = [...requisitionMessagesRoute, ...requisitionMessagesPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        RequisitionMessagesComponent,
        RequisitionMessagesDetailComponent,
        RequisitionMessagesUpdateComponent,
        RequisitionMessagesDeleteDialogComponent,
        RequisitionMessagesDeletePopupComponent
    ],
    entryComponents: [
        RequisitionMessagesComponent,
        RequisitionMessagesUpdateComponent,
        RequisitionMessagesDeleteDialogComponent,
        RequisitionMessagesDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionMessagesModule {}
