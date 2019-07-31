import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { requisitionExtendedPopupRoute, requisitionExtendedRoute } from 'app/entities/requisition-extended/requisition-extended.route';
import { RequisitionExtendedComponent } from 'app/entities/requisition-extended/requisition-extended.component';
import { RequisitionExtendedDetailComponent } from 'app/entities/requisition-extended/requisition-extended-detail.component';
import { RequisitionExtendedUpdateComponent } from 'app/entities/requisition-extended/requisition-extended-update.component';
import { RequisitionDeleteDialogComponent, RequisitionDeletePopupComponent } from 'app/entities/requisition';

const ENTITY_STATES = [...requisitionExtendedRoute, ...requisitionExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RequisitionExtendedComponent,
        RequisitionExtendedDetailComponent,
        RequisitionExtendedUpdateComponent,
        RequisitionDeleteDialogComponent,
        RequisitionDeletePopupComponent
    ],
    entryComponents: [
        RequisitionExtendedComponent,
        RequisitionExtendedUpdateComponent,
        RequisitionDeleteDialogComponent,
        RequisitionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiRequisitionExtendedModule {}
