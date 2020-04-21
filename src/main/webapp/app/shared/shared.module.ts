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

import {
    // @ts-ignore
    PurchaseOrderComponent,
    // @ts-ignore
    PurchaseOrderDeleteDialogComponent,
    // @ts-ignore
    PurchaseOrderDeletePopupComponent,
    // @ts-ignore
    PurchaseOrderDetailComponent,
    // @ts-ignore
    PurchaseOrderUpdateComponent
} from 'app/entities/purchase-order';
import {
    // @ts-ignore
    PurchaseOrderExtendedComponent,
    // @ts-ignore
    PurchaseOrderExtendedDetailComponent,
    // @ts-ignore
    PurchaseOrderExtendedUpdateComponent
} from 'app/entities/purchase-order-extended';
// @ts-ignore
import { TermsAndConditionsForPurchaseOrder } from 'app/entities/purchase-order-extended/terms-and-conditions-for-purchase-order';
// @ts-ignore
import { PurchaseOrderMessagesExtendedDirectiveComponent } from 'app/entities/purchase-order-extended/purchase-order-messages-extended-directive.component';
// @ts-ignore
import { PurchaseOrderRequisitionVoucherRelation } from 'app/entities/purchase-order-extended/purchase-order-requisition-voucher-relation.component';
// @ts-ignore
import { PurchaseOrderExtendedRequisitionDirectiveComponent } from 'app/entities/purchase-order-extended/purchase-order-extended-requisition-directive.component';
import { RequisitionComponent, RequisitionDetailComponent, RequisitionUpdateComponent } from 'app/entities/requisition';
import {
    RequisitionExtendedComponent,
    RequisitionExtendedDetailComponent,
    RequisitionExtendedUpdateComponent
} from 'app/entities/requisition-extended';
import { RequisitionDetailsExtendedDirectiveComponent } from 'app/entities/requisition-extended/requisition-details-extended-directive.component';
import { QuotationForRequisitionComponent } from 'app/entities/requisition-extended/quotation-for-requisition.component';
import { RequisitionMessagesDirectiveComponent } from 'app/entities/requisition-extended/requisition-messages-directive.component';
import { CommercialInfoDirComponent } from 'app/entities/requisition-extended/commercial-info-dir/commercial-info-dir.component';
import { RequisitionInfoCommercialDirComponent } from 'app/entities/requisition-extended/requisition-info-commercial-dir/requisition-info-commercial-dir.component';

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
        PurchaseOrderExtendedRequisitionDirectiveComponent,
        RequisitionComponent,
        RequisitionDetailComponent,
        RequisitionUpdateComponent,
        RequisitionExtendedComponent,
        RequisitionExtendedUpdateComponent,
        RequisitionExtendedDetailComponent,
        RequisitionDetailsExtendedDirectiveComponent,
        QuotationForRequisitionComponent,
        RequisitionMessagesDirectiveComponent,
        CommercialInfoDirComponent,
        RequisitionInfoCommercialDirComponent
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
        PurchaseOrderExtendedRequisitionDirectiveComponent,
        RequisitionComponent,
        RequisitionDetailComponent,
        RequisitionUpdateComponent,
        RequisitionExtendedComponent,
        RequisitionExtendedUpdateComponent,
        RequisitionExtendedDetailComponent,
        RequisitionDetailsExtendedDirectiveComponent,
        QuotationForRequisitionComponent,
        RequisitionMessagesDirectiveComponent,
        CommercialInfoDirComponent,
        RequisitionInfoCommercialDirComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
// @ts-ignore
export class SoptorshiSharedModule {
    static forRoot() {
        return {
            ngModule: SoptorshiSharedModule
        };
    }
}
