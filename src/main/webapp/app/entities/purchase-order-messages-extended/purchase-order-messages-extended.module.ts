import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PurchaseOrderMessagesExtendedComponent,
    PurchaseOrderMessagesExtendedDetailComponent,
    PurchaseOrderMessagesExtendedUpdateComponent,
    purchaseOrderMessagesExtendedRoute,
    purchaseOrderMessagesExtendedPopupRoute
} from './';
import {
    PurchaseOrderMessagesComponent,
    PurchaseOrderMessagesDeleteDialogComponent,
    PurchaseOrderMessagesDeletePopupComponent,
    PurchaseOrderMessagesDetailComponent,
    purchaseOrderMessagesPopupRoute,
    PurchaseOrderMessagesUpdateComponent
} from 'app/entities/purchase-order-messages';

const ENTITY_STATES = [...purchaseOrderMessagesExtendedRoute, ...purchaseOrderMessagesExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseOrderMessagesComponent,
        PurchaseOrderMessagesDetailComponent,
        PurchaseOrderMessagesUpdateComponent,
        PurchaseOrderMessagesExtendedComponent,
        PurchaseOrderMessagesExtendedDetailComponent,
        PurchaseOrderMessagesExtendedUpdateComponent,
        PurchaseOrderMessagesDeleteDialogComponent,
        PurchaseOrderMessagesDeletePopupComponent
    ],
    entryComponents: [
        PurchaseOrderMessagesExtendedComponent,
        PurchaseOrderMessagesExtendedUpdateComponent,
        PurchaseOrderMessagesDeleteDialogComponent,
        PurchaseOrderMessagesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseOrderMessagesModule {}
