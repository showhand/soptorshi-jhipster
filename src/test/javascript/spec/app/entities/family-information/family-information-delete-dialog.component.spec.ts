/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { FamilyInformationDeleteDialogComponent } from 'app/entities/family-information/family-information-delete-dialog.component';
import { FamilyInformationService } from 'app/entities/family-information/family-information.service';

describe('Component Tests', () => {
    describe('FamilyInformation Management Delete Component', () => {
        let comp: FamilyInformationDeleteDialogComponent;
        let fixture: ComponentFixture<FamilyInformationDeleteDialogComponent>;
        let service: FamilyInformationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FamilyInformationDeleteDialogComponent]
            })
                .overrideTemplate(FamilyInformationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FamilyInformationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FamilyInformationService);
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
