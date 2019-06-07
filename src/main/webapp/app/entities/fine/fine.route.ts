import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fine } from 'app/shared/model/fine.model';
import { FineService } from './fine.service';
import { FineComponent } from './fine.component';
import { FineDetailComponent } from './fine-detail.component';
import { FineUpdateComponent } from './fine-update.component';
import { FineDeletePopupComponent } from './fine-delete-dialog.component';
import { IFine } from 'app/shared/model/fine.model';

@Injectable({ providedIn: 'root' })
export class FineResolve implements Resolve<IFine> {
    constructor(private service: FineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFine> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeLongId = route.params['employeeLongId'] ? route.params['employeeLongId'] : null;
        console.log('id--->' + id);
        console.log('employee long id --->' + employeeLongId);
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Fine>) => response.ok),
                map((fine: HttpResponse<Fine>) => fine.body)
            );
        } else if (employeeLongId) {
            let fine = new Fine();
            fine.employeeId = employeeLongId;
            return of(fine);
        }
        return of(new Fine());
    }
}

export const fineRoute: Routes = [
    {
        path: '',
        component: FineComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Fines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/employee',
        component: FineComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            fine: FineResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Fines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FineDetailComponent,
        resolve: {
            fine: FineResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Fines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FineUpdateComponent,
        resolve: {
            fine: FineResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Fines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FineUpdateComponent,
        resolve: {
            fine: FineResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Fines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const finePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FineDeletePopupComponent,
        resolve: {
            fine: FineResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Fines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
