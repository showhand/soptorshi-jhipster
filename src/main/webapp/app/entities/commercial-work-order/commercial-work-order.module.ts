import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialWorkOrderComponent,
    CommercialWorkOrderDeleteDialogComponent,
    CommercialWorkOrderDeletePopupComponent,
    CommercialWorkOrderDetailComponent,
    commercialWorkOrderPopupRoute,
    commercialWorkOrderRoute,
    CommercialWorkOrderUpdateComponent
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
