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

const ENTITY_STATES = [...purchaseOrderExtendedRoute, ...purchaseOrderExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchaseOrderComponent,
        PurchaseOrderDetailComponent,
        PurchaseOrderUpdateComponent,
        PurchaseOrderExtendedComponent,
        PurchaseOrderExtendedDetailComponent,
        PurchaseOrderExtendedUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent,
        TermsAndConditionsForPurchaseOrder
    ],
    entryComponents: [
        PurchaseOrderExtendedComponent,
        PurchaseOrderExtendedUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPurchaseOrderExtendedModule {}
