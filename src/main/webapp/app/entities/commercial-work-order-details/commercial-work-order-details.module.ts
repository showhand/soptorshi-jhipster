import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialWorkOrderDetailsComponent,
    CommercialWorkOrderDetailsDeleteDialogComponent,
    CommercialWorkOrderDetailsDeletePopupComponent,
    CommercialWorkOrderDetailsDetailComponent,
    commercialWorkOrderDetailsPopupRoute,
    commercialWorkOrderDetailsRoute,
    CommercialWorkOrderDetailsUpdateComponent
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
