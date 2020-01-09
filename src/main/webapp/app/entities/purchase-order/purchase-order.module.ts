import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PurchaseOrderComponent,
    PurchaseOrderDetailComponent,
    PurchaseOrderUpdateComponent,
    PurchaseOrderDeletePopupComponent,
    PurchaseOrderDeleteDialogComponent,
    purchaseOrderRoute,
    purchaseOrderPopupRoute
} from './';

const ENTITY_STATES = [...purchaseOrderRoute, ...purchaseOrderPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        PurchaseOrderComponent,
        PurchaseOrderDetailComponent,
        PurchaseOrderUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent
    ],
    entryComponents: [
        PurchaseOrderComponent,
        PurchaseOrderUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseOrderModule {}
