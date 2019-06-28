import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    WorkOrderComponent,
    WorkOrderDetailComponent,
    WorkOrderUpdateComponent,
    WorkOrderDeletePopupComponent,
    WorkOrderDeleteDialogComponent,
    workOrderRoute,
    workOrderPopupRoute
} from './';

const ENTITY_STATES = [...workOrderRoute, ...workOrderPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WorkOrderComponent,
        WorkOrderDetailComponent,
        WorkOrderUpdateComponent,
        WorkOrderDeleteDialogComponent,
        WorkOrderDeletePopupComponent
    ],
    entryComponents: [WorkOrderComponent, WorkOrderUpdateComponent, WorkOrderDeleteDialogComponent, WorkOrderDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiWorkOrderModule {}
