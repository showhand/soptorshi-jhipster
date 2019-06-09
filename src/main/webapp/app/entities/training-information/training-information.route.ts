import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TrainingInformation } from 'app/shared/model/training-information.model';
import { TrainingInformationService } from './training-information.service';
import { TrainingInformationComponent } from './training-information.component';
import { TrainingInformationDetailComponent } from './training-information-detail.component';
import { TrainingInformationUpdateComponent } from './training-information-update.component';
import { TrainingInformationDeletePopupComponent } from './training-information-delete-dialog.component';
import { ITrainingInformation } from 'app/shared/model/training-information.model';

@Injectable({ providedIn: 'root' })
export class TrainingInformationResolve implements Resolve<ITrainingInformation> {
    constructor(private service: TrainingInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITrainingInformation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TrainingInformation>) => response.ok),
                map((trainingInformation: HttpResponse<TrainingInformation>) => trainingInformation.body)
            );
        } else if (employeeId) {
            const trainingInformation: ITrainingInformation = new TrainingInformation();
            trainingInformation.employeeId = employeeId;
            return of(trainingInformation);
        }
        return of(new TrainingInformation());
    }
}

export const trainingInformationRoute: Routes = [
    {
        path: '',
        component: TrainingInformationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'TrainingInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TrainingInformationDetailComponent,
        resolve: {
            trainingInformation: TrainingInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TrainingInformationUpdateComponent,
        resolve: {
            trainingInformation: TrainingInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: TrainingInformationUpdateComponent,
        resolve: {
            trainingInformation: TrainingInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TrainingInformationUpdateComponent,
        resolve: {
            trainingInformation: TrainingInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const trainingInformationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TrainingInformationDeletePopupComponent,
        resolve: {
            trainingInformation: TrainingInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'TrainingInformations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
