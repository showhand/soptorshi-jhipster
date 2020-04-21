import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PurchaseOrderVoucherRelationExtendedComponent,
    PurchaseOrderVoucherRelationExtendedDetailComponent,
    PurchaseOrderVoucherRelationExtendedUpdateComponent,
    PurchaseOrderVoucherRelationExtendedDeleteDialogComponent,
    purchaseOrderVoucherRelationExtendedRoute,
    purchaseOrderVoucherRelationPopupRoute,
    PurchaseOrderVoucherRelationExtendedDeletePopUpComponent
} from './';
import {
    PurchaseOrderVoucherRelationComponent,
    PurchaseOrderVoucherRelationDeleteDialogComponent,
    PurchaseOrderVoucherRelationDeletePopupComponent,
    PurchaseOrderVoucherRelationDetailComponent,
    PurchaseOrderVoucherRelationUpdateComponent
} from 'app/entities/purchase-order-voucher-relation';

const ENTITY_STATES = [...purchaseOrderVoucherRelationExtendedRoute, ...purchaseOrderVoucherRelationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseOrderVoucherRelationComponent,
        PurchaseOrderVoucherRelationDetailComponent,
        PurchaseOrderVoucherRelationUpdateComponent,
        PurchaseOrderVoucherRelationDeleteDialogComponent,
        PurchaseOrderVoucherRelationDeletePopupComponent,
        PurchaseOrderVoucherRelationExtendedComponent,
        PurchaseOrderVoucherRelationExtendedDetailComponent,
        PurchaseOrderVoucherRelationExtendedUpdateComponent,
        PurchaseOrderVoucherRelationExtendedDeleteDialogComponent,
        PurchaseOrderVoucherRelationExtendedDeletePopUpComponent
    ],
    entryComponents: [
        PurchaseOrderVoucherRelationExtendedComponent,
        PurchaseOrderVoucherRelationExtendedUpdateComponent,
        PurchaseOrderVoucherRelationExtendedDeleteDialogComponent,
        PurchaseOrderVoucherRelationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: []
})
export class SoptorshiPurchaseOrderVoucherRelationExtendedModule {}
