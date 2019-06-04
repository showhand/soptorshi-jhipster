/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { AllowanceManagementComponent } from 'app/entities/allowance-management/allowance-management.component';
import { AllowanceManagementService } from 'app/entities/allowance-management/allowance-management.service';
import { AllowanceManagement } from 'app/shared/model/allowance-management.model';

describe('Component Tests', () => {
    describe('AllowanceManagement Management Component', () => {
        let comp: AllowanceManagementComponent;
        let fixture: ComponentFixture<AllowanceManagementComponent>;
        let service: AllowanceManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AllowanceManagementComponent],
                providers: []
            })
                .overrideTemplate(AllowanceManagementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AllowanceManagementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AllowanceManagementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AllowanceManagement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.allowanceManagements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
