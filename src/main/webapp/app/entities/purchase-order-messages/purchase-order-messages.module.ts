import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PurchaseOrderMessagesComponent,
    PurchaseOrderMessagesDetailComponent,
    PurchaseOrderMessagesUpdateComponent,
    PurchaseOrderMessagesDeletePopupComponent,
    PurchaseOrderMessagesDeleteDialogComponent,
    purchaseOrderMessagesRoute,
    purchaseOrderMessagesPopupRoute
} from './';

const ENTITY_STATES = [...purchaseOrderMessagesRoute, ...purchaseOrderMessagesPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        PurchaseOrderMessagesComponent,
        PurchaseOrderMessagesDetailComponent,
        PurchaseOrderMessagesUpdateComponent,
        PurchaseOrderMessagesDeleteDialogComponent,
        PurchaseOrderMessagesDeletePopupComponent
    ],
    entryComponents: [
        PurchaseOrderMessagesComponent,
        PurchaseOrderMessagesUpdateComponent,
        PurchaseOrderMessagesDeleteDialogComponent,
        PurchaseOrderMessagesDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseOrderMessagesModule {}
