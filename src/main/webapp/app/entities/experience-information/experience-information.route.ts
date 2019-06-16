import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExperienceInformation } from 'app/shared/model/experience-information.model';
import { ExperienceInformationService } from './experience-information.service';
import { ExperienceInformationComponent } from './experience-information.component';
import { ExperienceInformationDetailComponent } from './experience-information-detail.component';
import { ExperienceInformationUpdateComponent } from './experience-information-update.component';
import { ExperienceInformationDeletePopupComponent } from './experience-information-delete-dialog.component';
import { IExperienceInformation } from 'app/shared/model/experience-information.model';

@Injectable({ providedIn: 'root' })
export class ExperienceInformationResolve implements Resolve<IExperienceInformation> {
    constructor(private service: ExperienceInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExperienceInformation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ExperienceInformation>) => response.ok),
                map((experienceInformation: HttpResponse<ExperienceInformation>) => experienceInformation.body)
            );
        } else if (employeeId) {
            const experienceInformation: IExperienceInformation = new ExperienceInformation();
            experienceInformation.employeeId = employeeId;
            return of(experienceInformation);
        }
        return of(new ExperienceInformation());
    }
}

export const experienceInformationRoute: Routes = [
    {
        path: '',
        component: ExperienceInformationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'ExperienceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ExperienceInformationDetailComponent,
        resolve: {
            experienceInformation: ExperienceInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'ExperienceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ExperienceInformationUpdateComponent,
        resolve: {
            experienceInformation: ExperienceInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'ExperienceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: ExperienceInformationUpdateComponent,
        resolve: {
            experienceInformation: ExperienceInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'ExperienceInformations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ExperienceInformationUpdateComponent,
        resolve: {
            experienceInformation: ExperienceInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'ExperienceInformations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const experienceInformationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ExperienceInformationDeletePopupComponent,
        resolve: {
            experienceInformation: ExperienceInformationResolve
        },
        data: {
            authorities: ['ROLE_EMPLOYEE_MANAGEMENT', 'ROLE_ADMIN'],
            pageTitle: 'ExperienceInformations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
