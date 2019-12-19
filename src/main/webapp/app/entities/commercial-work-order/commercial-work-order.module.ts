import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialWorkOrderComponent,
    CommercialWorkOrderDetailComponent,
    CommercialWorkOrderUpdateComponent,
    CommercialWorkOrderDeletePopupComponent,
    CommercialWorkOrderDeleteDialogComponent,
    commercialWorkOrderRoute,
    commercialWorkOrderPopupRoute
} from './';

const ENTITY_STATES = [...commercialWorkOrderRoute, ...commercialWorkOrderPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialWorkOrderComponent,
        CommercialWorkOrderDetailComponent,
        CommercialWorkOrderUpdateComponent,
        CommercialWorkOrderDeleteDialogComponent,
        CommercialWorkOrderDeletePopupComponent
    ],
    entryComponents: [
        CommercialWorkOrderComponent,
        CommercialWorkOrderUpdateComponent,
        CommercialWorkOrderDeleteDialogComponent,
        CommercialWorkOrderDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialWorkOrderModule {}
