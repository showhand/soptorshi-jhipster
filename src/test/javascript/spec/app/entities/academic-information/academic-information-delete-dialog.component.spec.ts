/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { AcademicInformationDeleteDialogComponent } from 'app/entities/academic-information/academic-information-delete-dialog.component';
import { AcademicInformationService } from 'app/entities/academic-information/academic-information.service';

describe('Component Tests', () => {
    describe('AcademicInformation Management Delete Component', () => {
        let comp: AcademicInformationDeleteDialogComponent;
        let fixture: ComponentFixture<AcademicInformationDeleteDialogComponent>;
        let service: AcademicInformationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AcademicInformationDeleteDialogComponent]
            })
                .overrideTemplate(AcademicInformationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcademicInformationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademicInformationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
