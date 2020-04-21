import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    RequisitionDetailsComponent,
    RequisitionDetailsDetailComponent,
    RequisitionDetailsUpdateComponent,
    RequisitionDetailsDeletePopupComponent,
    RequisitionDetailsDeleteDialogComponent,
    requisitionDetailsRoute,
    requisitionDetailsPopupRoute
} from './';

const ENTITY_STATES = [...requisitionDetailsRoute, ...requisitionDetailsPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        RequisitionDetailsComponent,
        RequisitionDetailsDetailComponent,
        RequisitionDetailsUpdateComponent,
        RequisitionDetailsDeleteDialogComponent,
        RequisitionDetailsDeletePopupComponent
    ],
    entryComponents: [
        RequisitionDetailsComponent,
        RequisitionDetailsUpdateComponent,
        RequisitionDetailsDeleteDialogComponent,
        RequisitionDetailsDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionDetailsModule {}
