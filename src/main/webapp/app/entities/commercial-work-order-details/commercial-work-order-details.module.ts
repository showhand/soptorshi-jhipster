import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialWorkOrderDetailsComponent,
    CommercialWorkOrderDetailsDetailComponent,
    CommercialWorkOrderDetailsUpdateComponent,
    CommercialWorkOrderDetailsDeletePopupComponent,
    CommercialWorkOrderDetailsDeleteDialogComponent,
    commercialWorkOrderDetailsRoute,
    commercialWorkOrderDetailsPopupRoute
} from './';

const ENTITY_STATES = [...commercialWorkOrderDetailsRoute, ...commercialWorkOrderDetailsPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialWorkOrderDetailsComponent,
        CommercialWorkOrderDetailsDetailComponent,
        CommercialWorkOrderDetailsUpdateComponent,
        CommercialWorkOrderDetailsDeleteDialogComponent,
        CommercialWorkOrderDetailsDeletePopupComponent
    ],
    entryComponents: [
        CommercialWorkOrderDetailsComponent,
        CommercialWorkOrderDetailsUpdateComponent,
        CommercialWorkOrderDetailsDeleteDialogComponent,
        CommercialWorkOrderDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialWorkOrderDetailsModule {}
