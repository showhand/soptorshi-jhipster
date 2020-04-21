import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { requisitionExtendedPopupRoute, requisitionExtendedRoute } from 'app/entities/requisition-extended/requisition-extended.route';
import { RequisitionExtendedComponent } from 'app/entities/requisition-extended/requisition-extended.component';
import { RequisitionExtendedDetailComponent } from 'app/entities/requisition-extended/requisition-extended-detail.component';
import { RequisitionExtendedUpdateComponent } from 'app/entities/requisition-extended/requisition-extended-update.component';
import {
    RequisitionComponent,
    RequisitionDeleteDialogComponent,
    RequisitionDeletePopupComponent,
    RequisitionDetailComponent,
    RequisitionUpdateComponent
} from 'app/entities/requisition';
import { RequisitionDetailsExtendedDirectiveComponent } from 'app/entities/requisition-extended/requisition-details-extended-directive.component';
import { QuotationForRequisitionComponent } from 'app/entities/requisition-extended/quotation-for-requisition.component';
import { RequisitionMessagesDirectiveComponent } from 'app/entities/requisition-extended/requisition-messages-directive.component';
import { CommercialInfoDirComponent } from './commercial-info-dir/commercial-info-dir.component';
import { RequisitionInfoCommercialDirComponent } from './requisition-info-commercial-dir/requisition-info-commercial-dir.component';

const ENTITY_STATES = [...requisitionExtendedRoute, ...requisitionExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        /*     RequisitionComponent,
        RequisitionDetailComponent,
        RequisitionUpdateComponent,
        RequisitionExtendedComponent,
        RequisitionExtendedUpdateComponent,
        RequisitionExtendedDetailComponent,*/
        RequisitionDeleteDialogComponent,
        RequisitionDeletePopupComponent /*,
        RequisitionDetailsExtendedDirectiveComponent,
        QuotationForRequisitionComponent,
        RequisitionMessagesDirectiveComponent,
        CommercialInfoDirComponent,
        RequisitionInfoCommercialDirComponent*/
    ],
    entryComponents: [
        RequisitionExtendedComponent,
        RequisitionExtendedUpdateComponent,
        RequisitionDeleteDialogComponent,
        RequisitionExtendedDetailComponent,
        RequisitionDeletePopupComponent,
        RequisitionDetailsExtendedDirectiveComponent,
        QuotationForRequisitionComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionExtendedModule {}
