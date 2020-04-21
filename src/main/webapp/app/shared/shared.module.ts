import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { SoptorshiSharedLibsModule, SoptorshiSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import {
    PurchaseOrderVoucherRelationComponent,
    PurchaseOrderVoucherRelationDeleteDialogComponent,
    PurchaseOrderVoucherRelationDeletePopupComponent,
    PurchaseOrderVoucherRelationDetailComponent,
    PurchaseOrderVoucherRelationUpdateComponent
} from 'app/entities/purchase-order-voucher-relation';
import {
    PurchaseOrderVoucherRelationExtendedComponent,
    PurchaseOrderVoucherRelationExtendedDeleteDialogComponent,
    PurchaseOrderVoucherRelationExtendedDeletePopUpComponent,
    PurchaseOrderVoucherRelationExtendedDetailComponent,
    PurchaseOrderVoucherRelationExtendedUpdateComponent
} from 'app/entities/purchase-order-voucher-relation-extended';
import { RouterModule } from '@angular/router';
import { PURCHASE_ORDER_VOUCHER_RELATION_ENTITY_STATES } from 'app/entities/purchase-order-voucher-relation-extended/purchase-order-voucher-relation-extended.module';
import {
    PurchaseOrderComponent,
    PurchaseOrderDeleteDialogComponent,
    PurchaseOrderDeletePopupComponent,
    PurchaseOrderDetailComponent,
    PurchaseOrderUpdateComponent
} from 'app/entities/purchase-order';
import {
    PurchaseOrderExtendedComponent,
    PurchaseOrderExtendedDetailComponent,
    PurchaseOrderExtendedUpdateComponent
} from 'app/entities/purchase-order-extended';
import { TermsAndConditionsForPurchaseOrder } from 'app/entities/purchase-order-extended/terms-and-conditions-for-purchase-order';
import { PurchaseOrderMessagesExtendedDirectiveComponent } from 'app/entities/purchase-order-extended/purchase-order-messages-extended-directive.component';
import { PurchaseOrderRequisitionVoucherRelation } from 'app/entities/purchase-order-extended/purchase-order-requisition-voucher-relation.component';
import { PurchaseOrderExtendedRequisitionDirectiveComponent } from 'app/entities/purchase-order-extended/purchase-order-extended-requisition-directive.component';

@NgModule({
    imports: [SoptorshiSharedLibsModule, SoptorshiSharedCommonModule, RouterModule],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        PurchaseOrderVoucherRelationComponent,
        PurchaseOrderVoucherRelationDetailComponent,
        PurchaseOrderVoucherRelationUpdateComponent,
        PurchaseOrderVoucherRelationDeleteDialogComponent,
        PurchaseOrderVoucherRelationDeletePopupComponent,
        PurchaseOrderVoucherRelationExtendedComponent,
        PurchaseOrderVoucherRelationExtendedDetailComponent,
        PurchaseOrderVoucherRelationExtendedUpdateComponent,
        PurchaseOrderVoucherRelationExtendedDeleteDialogComponent,
        PurchaseOrderVoucherRelationExtendedDeletePopUpComponent,
        PurchaseOrderComponent,
        PurchaseOrderDetailComponent,
        PurchaseOrderUpdateComponent,
        PurchaseOrderExtendedComponent,
        PurchaseOrderExtendedDetailComponent,
        PurchaseOrderExtendedUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent,
        TermsAndConditionsForPurchaseOrder,
        PurchaseOrderMessagesExtendedDirectiveComponent,
        PurchaseOrderRequisitionVoucherRelation,
        PurchaseOrderExtendedRequisitionDirectiveComponent
    ],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        SoptorshiSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        PurchaseOrderVoucherRelationComponent,
        PurchaseOrderVoucherRelationDetailComponent,
        PurchaseOrderVoucherRelationUpdateComponent,
        PurchaseOrderVoucherRelationDeleteDialogComponent,
        PurchaseOrderVoucherRelationDeletePopupComponent,
        PurchaseOrderVoucherRelationExtendedComponent,
        PurchaseOrderVoucherRelationExtendedDetailComponent,
        PurchaseOrderVoucherRelationExtendedUpdateComponent,
        PurchaseOrderComponent,
        PurchaseOrderDetailComponent,
        PurchaseOrderUpdateComponent,
        PurchaseOrderExtendedComponent,
        PurchaseOrderExtendedDetailComponent,
        PurchaseOrderExtendedUpdateComponent,
        PurchaseOrderDeleteDialogComponent,
        PurchaseOrderDeletePopupComponent,
        TermsAndConditionsForPurchaseOrder,
        PurchaseOrderMessagesExtendedDirectiveComponent,
        PurchaseOrderRequisitionVoucherRelation,
        PurchaseOrderExtendedRequisitionDirectiveComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSharedModule {
    static forRoot() {
        return {
            ngModule: SoptorshiSharedModule
        };
    }
}
