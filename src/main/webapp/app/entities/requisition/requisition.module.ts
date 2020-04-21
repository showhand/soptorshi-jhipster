import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    RequisitionComponent,
    RequisitionDetailComponent,
    RequisitionUpdateComponent,
    RequisitionDeletePopupComponent,
    RequisitionDeleteDialogComponent,
    requisitionRoute,
    requisitionPopupRoute
} from './';

const ENTITY_STATES = [...requisitionRoute, ...requisitionPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*    declarations: [
        RequisitionComponent,
        RequisitionDetailComponent,
        RequisitionUpdateComponent,
        RequisitionDeleteDialogComponent,
        RequisitionDeletePopupComponent
    ],
    entryComponents: [RequisitionComponent, RequisitionUpdateComponent, RequisitionDeleteDialogComponent, RequisitionDeletePopupComponent],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionModule {}
