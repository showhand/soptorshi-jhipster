import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    RequisitionVoucherRelationComponent,
    RequisitionVoucherRelationDetailComponent,
    RequisitionVoucherRelationUpdateComponent,
    RequisitionVoucherRelationDeletePopupComponent,
    RequisitionVoucherRelationDeleteDialogComponent,
    requisitionVoucherRelationRoute,
    requisitionVoucherRelationPopupRoute
} from './';

const ENTITY_STATES = [...requisitionVoucherRelationRoute, ...requisitionVoucherRelationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        RequisitionVoucherRelationComponent,
        RequisitionVoucherRelationDetailComponent,
        RequisitionVoucherRelationUpdateComponent,
        RequisitionVoucherRelationDeleteDialogComponent,
        RequisitionVoucherRelationDeletePopupComponent
    ],
    entryComponents: [
        RequisitionVoucherRelationComponent,
        RequisitionVoucherRelationUpdateComponent,
        RequisitionVoucherRelationDeleteDialogComponent,
        RequisitionVoucherRelationDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionVoucherRelationModule {}
