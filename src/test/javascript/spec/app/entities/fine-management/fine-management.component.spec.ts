/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { FineManagementComponent } from 'app/entities/fine-management/fine-management.component';
import { FineManagementService } from 'app/entities/fine-management/fine-management.service';
import { FineManagement } from 'app/shared/model/fine-management.model';

describe('Component Tests', () => {
    describe('FineManagement Management Component', () => {
        let comp: FineManagementComponent;
        let fixture: ComponentFixture<FineManagementComponent>;
        let service: FineManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineManagementComponent],
                providers: []
            })
                .overrideTemplate(FineManagementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FineManagementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FineManagementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FineManagement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fineManagements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
