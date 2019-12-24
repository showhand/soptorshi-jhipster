import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPoComponent,
    CommercialPoDeleteDialogComponent,
    CommercialPoDeletePopupComponent,
    CommercialPoDetailComponent,
    commercialPoPopupRoute,
    commercialPoRoute,
    CommercialPoUpdateComponent
} from './';

const ENTITY_STATES = [...commercialPoRoute, ...commercialPoPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPoComponent,
        CommercialPoDetailComponent,
        CommercialPoUpdateComponent,
        CommercialPoDeleteDialogComponent,
        CommercialPoDeletePopupComponent
    ],
    entryComponents: [
        CommercialPoComponent,
        CommercialPoUpdateComponent,
        CommercialPoDeleteDialogComponent,
        CommercialPoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPoModule {}
