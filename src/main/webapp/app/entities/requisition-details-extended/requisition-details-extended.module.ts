import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    RequisitionDetailsComponent,
    RequisitionDetailsDeleteDialogComponent,
    RequisitionDetailsDeletePopupComponent,
    RequisitionDetailsDetailComponent,
    RequisitionDetailsUpdateComponent
} from 'app/entities/requisition-details';
import { RequisitionDetailsExtendedUpdateComponent } from 'app/entities/requisition-details-extended/requisition-details-extended-update.component';
import { RequisitionDetailsExtendedDetailComponent } from 'app/entities/requisition-details-extended/requisition-details-extended-detail.component';
import { RequisitionDetailsExtendedComponent } from 'app/entities/requisition-details-extended/requisition-details-extended.component';
import {
    requisitionDetailsExtendedPopupRoute,
    requisitionDetailsExtendedRoute
} from 'app/entities/requisition-details-extended/requisition-details-extended.route';

const ENTITY_STATES = [...requisitionDetailsExtendedRoute, ...requisitionDetailsExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RequisitionDetailsComponent,
        RequisitionDetailsDetailComponent,
        RequisitionDetailsUpdateComponent,
        RequisitionDetailsExtendedComponent,
        RequisitionDetailsExtendedDetailComponent,
        RequisitionDetailsExtendedUpdateComponent,
        RequisitionDetailsDeleteDialogComponent,
        RequisitionDetailsDeletePopupComponent
    ],
    entryComponents: [
        RequisitionDetailsExtendedComponent,
        RequisitionDetailsExtendedDetailComponent,
        RequisitionDetailsExtendedUpdateComponent,
        RequisitionDetailsDeleteDialogComponent,
        RequisitionDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionDetailsExtendedModule {}
