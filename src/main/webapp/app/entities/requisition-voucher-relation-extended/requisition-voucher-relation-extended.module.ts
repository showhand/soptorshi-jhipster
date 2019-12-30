import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    RequisitionVoucherRelationExtendedComponent,
    RequisitionVoucherRelationExtendedDetailComponent,
    RequisitionVoucherRelationExtendedUpdateComponent,
    requisitionVoucherRelationExtendedRoute
} from './';
import {
    RequisitionVoucherRelationComponent,
    RequisitionVoucherRelationDeleteDialogComponent,
    RequisitionVoucherRelationDeletePopupComponent,
    RequisitionVoucherRelationDetailComponent,
    requisitionVoucherRelationPopupRoute,
    RequisitionVoucherRelationUpdateComponent
} from 'app/entities/requisition-voucher-relation';

const ENTITY_STATES = [...requisitionVoucherRelationExtendedRoute, ...requisitionVoucherRelationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RequisitionVoucherRelationComponent,
        RequisitionVoucherRelationDetailComponent,
        RequisitionVoucherRelationUpdateComponent,
        RequisitionVoucherRelationExtendedComponent,
        RequisitionVoucherRelationExtendedDetailComponent,
        RequisitionVoucherRelationExtendedUpdateComponent,
        RequisitionVoucherRelationDeleteDialogComponent,
        RequisitionVoucherRelationDeletePopupComponent
    ],
    entryComponents: [
        RequisitionVoucherRelationExtendedComponent,
        RequisitionVoucherRelationExtendedUpdateComponent,
        RequisitionVoucherRelationDeleteDialogComponent,
        RequisitionVoucherRelationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionVoucherRelationModule {}
