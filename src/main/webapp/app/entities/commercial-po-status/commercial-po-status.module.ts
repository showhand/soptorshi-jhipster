import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialPoStatusComponent,
    CommercialPoStatusDeleteDialogComponent,
    CommercialPoStatusDeletePopupComponent,
    CommercialPoStatusDetailComponent,
    commercialPoStatusPopupRoute,
    commercialPoStatusRoute,
    CommercialPoStatusUpdateComponent
} from './';

const ENTITY_STATES = [...commercialPoStatusRoute, ...commercialPoStatusPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialPoStatusComponent,
        CommercialPoStatusDetailComponent,
        CommercialPoStatusUpdateComponent,
        CommercialPoStatusDeleteDialogComponent,
        CommercialPoStatusDeletePopupComponent
    ],
    entryComponents: [
        CommercialPoStatusComponent,
        CommercialPoStatusUpdateComponent,
        CommercialPoStatusDeleteDialogComponent,
        CommercialPoStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialPoStatusModule {}
