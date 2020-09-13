import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { HasAnyAuthorityDirective, JhiLoginModalComponent, SoptorshiSharedCommonModule, SoptorshiSharedLibsModule } from './';
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
import { ScmZoneManagerFilterPipe } from 'app/shared/util/scm-zone-manager-filter.pipe';
import { ScmAreaManagerFilterPipe } from 'app/shared/util/scm-area-manager-filter.pipe';
import { ScmOrderDetailsFilterPipe } from 'app/shared/util/scm-order-details-filter.pipe';
import { LeaveAttachmentFilterPipe } from 'app/shared/util/leave-attachment-filter.pipe';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
    imports: [SoptorshiSharedLibsModule, SoptorshiSharedCommonModule, RouterModule, ToastrModule.forRoot()],
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
        RequisitionInfoCommercialDirComponent,
        ScmZoneManagerFilterPipe,
        ScmAreaManagerFilterPipe,
        ScmOrderDetailsFilterPipe,
        LeaveAttachmentFilterPipe
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
        RequisitionInfoCommercialDirComponent,
        ScmZoneManagerFilterPipe,
        ScmAreaManagerFilterPipe,
        ScmOrderDetailsFilterPipe,
        LeaveAttachmentFilterPipe
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
