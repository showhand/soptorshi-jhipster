/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { AdvanceManagementComponent } from 'app/entities/advance-management/advance-management.component';
import { AdvanceManagementService } from 'app/entities/advance-management/advance-management.service';
import { AdvanceManagement } from 'app/shared/model/advance-management.model';

describe('Component Tests', () => {
    describe('AdvanceManagement Management Component', () => {
        let comp: AdvanceManagementComponent;
        let fixture: ComponentFixture<AdvanceManagementComponent>;
        let service: AdvanceManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AdvanceManagementComponent],
                providers: []
            })
                .overrideTemplate(AdvanceManagementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdvanceManagementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvanceManagementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AdvanceManagement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.advanceManagements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
