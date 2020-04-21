import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PurchaseOrderComponent,
    PurchaseOrderDeleteDialogComponent,
    PurchaseOrderDeletePopupComponent,
    PurchaseOrderDetailComponent,
    PurchaseOrderUpdateComponent
} from 'app/entities/purchase-order';
import { PurchaseOrderExtendedUpdateComponent } from 'app/entities/purchase-order-extended/purchase-order-extended-update.component';
import { PurchaseOrderExtendedComponent } from 'app/entities/purchase-order-extended/purchase-order-extended.component';
import { PurchaseOrderExtendedDetailComponent } from 'app/entities/purchase-order-extended/purchase-order-extended-detail.component';
import {
    purchaseOrderExtendedPopupRoute,
    purchaseOrderExtendedRoute
} from 'app/entities/purchase-order-extended/purchase-order-extended.route';
import { TermsAndConditionsForPurchaseOrder } from 'app/entities/purchase-order-extended/terms-and-conditions-for-purchase-order';
import { PurchaseOrderMessagesExtendedDirectiveComponent } from 'app/entities/purchase-order-extended/purchase-order-messages-extended-directive.component';
import { PurchaseOrderRequisitionVoucherRelation } from 'app/entities/purchase-order-extended/purchase-order-requisition-voucher-relation.component';
import { SoptorshiPurchaseOrderVoucherRelationExtendedModule } from 'app/entities/purchase-order-voucher-relation-extended/purchase-order-voucher-relation-extended.module';

const PURCHASE_ORDER_ENTITY_STATES = [...purchaseOrderExtendedRoute, ...purchaseOrderExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(PURCHASE_ORDER_ENTITY_STATES)],
    declarations: [
        /* PurchaseOrderComponent,
        PurchaseOrderDetailComponent,
        PurchaseOrderUpdateComponent,
        PurchaseOrderExtendedComponent,
        PurchaseOrderExtendedDetailComponent,
        PurchaseOrderExtendedUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent,
        TermsAndConditionsForPurchaseOrder,
        PurchaseOrderMessagesExtendedDirectiveComponent,
        PurchaseOrderRequisitionVoucherRelation*/
    ],
    entryComponents: [
        PurchaseOrderExtendedComponent,
        PurchaseOrderExtendedUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent,
        PurchaseOrderRequisitionVoucherRelation
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseOrderExtendedModule {}
