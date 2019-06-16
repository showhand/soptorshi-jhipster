/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { ProvidentManagementComponent } from 'app/entities/provident-management/provident-management.component';
import { ProvidentManagementService } from 'app/entities/provident-management/provident-management.service';
import { ProvidentManagement } from 'app/shared/model/provident-management.model';

describe('Component Tests', () => {
    describe('ProvidentManagement Management Component', () => {
        let comp: ProvidentManagementComponent;
        let fixture: ComponentFixture<ProvidentManagementComponent>;
        let service: ProvidentManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProvidentManagementComponent],
                providers: []
            })
                .overrideTemplate(ProvidentManagementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProvidentManagementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProvidentManagementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProvidentManagement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.providentManagements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
